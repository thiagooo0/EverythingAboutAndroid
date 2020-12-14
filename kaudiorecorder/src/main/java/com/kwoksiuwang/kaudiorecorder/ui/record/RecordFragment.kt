package com.kwoksiuwang.kaudiorecorder.ui.record

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kwoksiuwang.kaudiorecorder.R
import com.kwoksiuwang.kaudiorecorder.databinding.FragmentRecordBinding
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment() {

    private lateinit var recordViewModel: RecordViewModel

    private var lastRecordStatus = RecordViewModel.recordStatusIdle
    private val btnShowAnimate: AnimatorSet by lazy {
        val set = AnimatorSet()
        set.playTogether(
            ObjectAnimator.ofFloat(btn_finish, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(btn_cancel, "alpha", 0f, 1f)
        )
        set
    }
    private val btnHideAnimate: AnimatorSet by lazy {
        val set = AnimatorSet()
        set.playTogether(
            ObjectAnimator.ofFloat(btn_finish, "alpha", 1f, 0f),
            ObjectAnimator.ofFloat(btn_cancel, "alpha", 1f, 0f)
        )
        set
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recordViewModel =
            ViewModelProvider(this).get(RecordViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentRecordBinding>(
            inflater,
            R.layout.fragment_record,
            container,
            false
        )
        binding.recordVM = recordViewModel
//        initViewModelObserver()
        return binding.root
    }


    private fun initViewModelObserver() {
        recordViewModel.recordStatus.observe(viewLifecycleOwner,
            Observer<Int> { status ->
                when (status) {
                    RecordViewModel.recordStatusPause -> {
                        btnShowAnimate.start()
                    }
                    else -> {
                        if (lastRecordStatus == RecordViewModel.recordStatusPause) {
                            btnHideAnimate.start()
                        }
                    }
                }
                lastRecordStatus = status
            })
    }
}
