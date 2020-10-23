package com.kwoksiuwang.kaudiorecorder.ui.record

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kwoksiuwang.kmedia.encoder.controller.MuxerController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * todo 第二次录制的时候存在 0-duration samples found: 1的问题
 */
class RecordViewModel(application: Application) : AndroidViewModel(application) {
    private val tag = "RecordViewModel"
    private val muxerController: MuxerController by lazy { MuxerController() }

    companion object {
        const val recordStatusIdle = 0
        const val recordStatusInit = 1
        const val recordStatusRunning = 2
        const val recordStatusPause = 3
        const val recordStatusStopping = 4
    }

    val recordStatus = MutableLiveData(recordStatusIdle)

    private var initJob: Job? = null

    private val timeFormat: SimpleDateFormat by lazy {
        SimpleDateFormat(
            "MM:dd-hh:mm:ss",
            Locale.getDefault()
        )
    }

    init {
        initJob = viewModelScope.launch(Dispatchers.Default) {
            muxerController.init()
            withContext(Dispatchers.Main) {
                recordStatus.value = recordStatusInit
                Log.d(tag, "init finish")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        muxerController.release()
    }

    fun recordBtnClick() {
        Log.d(tag, "recordClick() currentStatus ${recordStatus.value}")
        viewModelScope.launch(Dispatchers.Default) {
            Log.d(tag, "recordClick() thread : ${Thread.currentThread().name}")
            initJob?.join()
            when (recordStatus.value) {
                recordStatusInit -> {
                    //start
                    muxerController.start(
                        getApplication<Application>().getExternalFilesDir("audio")!!.path + "/audio_${timeFormat.format(
                            System.currentTimeMillis()
                        )}.mp4"
                    )
                    withContext(Dispatchers.Main) {
                        recordStatus.value = recordStatusRunning
                    }
                }
                recordStatusRunning -> {
                    //pause
                    muxerController.pause()
                    withContext(Dispatchers.Main) {
                        recordStatus.value = recordStatusPause
                    }
                }
                recordStatusPause -> {
                    muxerController.restart()
                    withContext(Dispatchers.Main) {
                        recordStatus.value = recordStatusRunning
                    }
                }
                else -> {

                }
            }
        }
    }

    fun finishClick() {
        viewModelScope.launch {
            recordStatus.value = recordStatusStopping
            withContext(Dispatchers.Default) {
                muxerController.stop()
            }
            recordStatus.value = recordStatusInit
        }
    }
}