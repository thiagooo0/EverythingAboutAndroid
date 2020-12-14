package com.kowksiuwang.everythingaboutandroid.media.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kowksiuwang.everythingaboutandroid.R
import kotlinx.android.synthetic.main.activity_camera.*
import javax.security.auth.callback.Callback

class CameraActivity : AppCompatActivity() {
    private val tag = "CameraActivity"
    private var surface: Surface? = null
    private val mHandler: Handler by lazy {
        val thread = HandlerThread("camera")
        thread.start()
        val handler = Handler(thread.looper)
        handler
    }
    private val mHandler2: Handler by lazy {
        val thread = HandlerThread("camera")
        thread.start()
        val handler = Handler(thread.looper)
        handler
    }

    private var cameraDevice: CameraDevice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        surface_view.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(
                holder: SurfaceHolder?,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d(tag, "surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                surface = null
                Log.d(tag, "surfaceDestroyed")
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                surface = holder?.surface
                initCamera(true)
                Log.d(tag, "surfaceCreated")
            }

        })

        btn_record_video.setOnClickListener {
            if (surface == null) {
                Log.d(tag, "surface is not ready")
                return@setOnClickListener
            } else {

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initCamera(isFront: Boolean) {
        try {
            val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            if (cameraManager != null) {
                val cameraList = cameraManager.cameraIdList
                for (c in cameraList) {
                    Log.d(tag, "--getCamera $c")
                }
                cameraManager.openCamera(
                    if (isFront) cameraList[0] else cameraList[1],
                    object : CameraDevice.StateCallback() {
                        override fun onOpened(camera: CameraDevice) {
                            cameraDevice = camera
                            startEncoder()
                            val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                            val surfaceList = ArrayList<Surface>()
                            encodeSurface?.let {
                                builder.addTarget(it)
                                surfaceList.add(it)
                                Log.d(tag, "添加解码surface")
                            }
                            surface?.let {
                                builder.addTarget(it)
                                surfaceList.add(it)
                                Log.d(tag, "添加预览surface")
                            }
                            camera.createCaptureSession(surfaceList, object :
                                CameraCaptureSession.StateCallback() {
                                override fun onConfigureFailed(session: CameraCaptureSession) {
                                }

                                override fun onConfigured(session: CameraCaptureSession) {
                                    session.setRepeatingRequest(builder.build(), null, mHandler)
                                }
                            }, mHandler)
                        }

                        override fun onDisconnected(camera: CameraDevice) {
                        }

                        override fun onError(camera: CameraDevice, error: Int) {
                        }
                    },
                    mHandler
                )
            } else {
                Log.d(tag, "initCamera fall")
            }
        } catch (e: Exception) {

        }
    }

    private var encoder: MediaCodec? = null
    private var encodeSurface: Surface? = null
    private fun startEncoder() {
        try {
            encoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_HEVC)
            val mediaFormat =
                MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_HEVC, 1920, 1080)
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 2000000)
            mediaFormat.setInteger(
                MediaFormat.KEY_BITRATE_MODE,
                MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR
            )
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30)
            mediaFormat.setInteger(
                MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
            )
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
            encoder?.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                encoder?.setCallback(object : MediaCodec.Callback() {
                    override fun onOutputBufferAvailable(
                        codec: MediaCodec,
                        index: Int,
                        info: MediaCodec.BufferInfo
                    ) {
                        codec.releaseOutputBuffer(index, false)
                    }

                    override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
                    }

                    override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
                    }

                    override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
                    }

                }, mHandler2)
            }
            //只有在configure完成start之前调用
            encodeSurface = encoder?.createInputSurface()
            encoder?.start()
        } catch (e: java.lang.Exception) {
            Log.d(tag, "startEncode fail : $e")
        }
    }
}