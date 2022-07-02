package com.mobulous.fitscope.activity.BlutoothDevice

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.FoundDeviceAdapter

import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.profile.ProfileActivity
import com.mobulous.fitscope.databinding.ActivityFoundDeviceBinding
import com.mobulous.fitscope.databinding.DialogDownloadingLayBinding

import com.mobulous.model.FoundDeviceModel
import java.util.ArrayList

class FoundDeviceActivity : AppCompatActivity() {
    lateinit var bin: ActivityFoundDeviceBinding
    private var lstFoundDevice = ArrayList<FoundDeviceModel>()
    lateinit var adptFoundD: FoundDeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityFoundDeviceBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        listnr()

    }


    private fun initView() {
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.earpod_black_img,
                "Zeeshan's Earpods",
                R.drawable.bluetooth_finding_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.watch,
                "Zeeshan's iWatch",
                R.drawable.bluetooth_notfind_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.watch1,
                "My Fitbit",
                R.drawable.bluetooth_paired_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.exercise_cycle_img,
                "BKPOOL",
                R.drawable.bluetooth_finding_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.elliptical_1_img,
                "ELITE",
                R.drawable.bluetooth_notfind_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.watch,
                "Zeeshan's iWatch",
                R.drawable.bluetooth_notfind_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.watch1,
                "My Fitbit",
                R.drawable.bluetooth_paired_img
            )
        )
        lstFoundDevice.add(
            FoundDeviceModel(
                R.drawable.exercise_cycle_img,
                "BKPOOL",
                R.drawable.bluetooth_finding_img
            )
        )


        adptFoundD = FoundDeviceAdapter(this, lstFoundDevice).apply {
            bin.rcyFoundDevice.layoutManager = LinearLayoutManager(this@FoundDeviceActivity)
            bin.rcyFoundDevice.adapter = this
        }
    }

    private fun listnr() {

    }


}