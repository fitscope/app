package com.mobulous.fitscope.activity.BlutoothDevice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobulous.fitscope.databinding.ActivitySearchDeviceBinding

class SearchDeviceActivity : AppCompatActivity() {
    lateinit var bin: ActivitySearchDeviceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivitySearchDeviceBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        listnr()
    }

    private fun initView() {

    }

    private fun listnr() {
        bin.tvSearchDevice.setOnClickListener {
            startActivity(Intent(this, FoundDeviceActivity::class.java))
        }
        bin.ivback.setOnClickListener {
            finish()
        }

    }
}