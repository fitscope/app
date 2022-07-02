package com.mobulous

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let {
            if (it.getStringExtra("data") != null) {
                println("========>${it.getStringExtra("data")}")
            }
        }
    }
}