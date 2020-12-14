package com.kowksiuwang.everythingaboutandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val i = 3
        val hashmap = HashMap<Int, Int>()
        Collections.synchronizedMap(HashMap<Int, Int>())
    }
}