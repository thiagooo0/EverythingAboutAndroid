package com.kwoksiuwang.kaudiorecorder.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kwoksiuwang.kaudiorecorder.R

class RecordFragment : Fragment() {

    private lateinit var recordViewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recordViewModel =
            ViewModelProvider(this).get(RecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_record, container, false)
        return root
    }
}
