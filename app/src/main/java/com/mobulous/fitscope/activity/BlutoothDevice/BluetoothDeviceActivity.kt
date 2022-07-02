package com.mobulous.fitscope.activity.BlutoothDevice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.BluetoothDeviceAdapter
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.ActivityBluetoothDeviceBinding
import com.mobulous.model.BluetoothDeviceModel
import java.util.ArrayList

class BluetoothDeviceActivity : AppCompatActivity() {
    lateinit var bin: ActivityBluetoothDeviceBinding
    private var lstBluetooth = ArrayList<BluetoothDeviceModel>()
    lateinit var adptrBluetooth: BluetoothDeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityBluetoothDeviceBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        listnr()
    }

    private fun initView() {
        lstBluetooth.add(
            BluetoothDeviceModel(
                "Apple Watch",
                "Series 3 with GPS",
                "Tap to connect with your Apple Watch via Bluetooth",
                "Not Paired",
                R.drawable.watch
            )
        )
        lstBluetooth.add(
            BluetoothDeviceModel(
                "Fitbit",
                "Versa 3",
                "Tap to connect with your Apple Watch via Bluetooth",
                "Not Paired",
                R.drawable.watch
            )
        )
        lstBluetooth.add(
            BluetoothDeviceModel(
                "Exercise Bike",
                "GO12454",
                "Tap to connect with your Apple Watch via Bluetooth",
                "Not Paired",
                R.drawable.watch
            )
        )
        lstBluetooth.add(
            BluetoothDeviceModel(
                "Treadmill",
                "GO12454",
                "Tap to connect with your Apple Watch via Bluetooth",
                "Not Paired",
                R.drawable.watch
            )
        )
        lstBluetooth.add(
            BluetoothDeviceModel(
                "Rowing",
                "GO12454",
                "Tap to connect with your Apple Watch via Bluetooth",
                "Not Paired",
                R.drawable.watch
            )
        )

        adptrBluetooth = BluetoothDeviceAdapter(this, lstBluetooth).apply {
            bin.rcyBlutoothDevice.layoutManager = LinearLayoutManager(this@BluetoothDeviceActivity)
            bin.rcyBlutoothDevice.adapter = this

        }

        bin.ToolbarBluetoothToolbar.ivback.visibility = View.VISIBLE
        bin.ToolbarBluetoothToolbar.ivMainNotification.visibility = View.GONE
        bin.ToolbarBluetoothToolbar.ivDrawer.visibility = View.GONE
        bin.ToolbarBluetoothToolbar.tvTitle.visibility = View.VISIBLE
        bin.ToolbarBluetoothToolbar.tvTitle.text = "Select Devices"
        bin.ToolbarBluetoothToolbar.ivMainLogo.visibility = View.GONE
    }

    private fun listnr() {
        bin.ToolbarBluetoothToolbar.ivback.setOnClickListener {
            onBackPressed()
        }
        bin.tvAddDeviceBluetooth.setOnClickListener {
            startActivity(Intent(this,SearchDeviceActivity::class.java))
        }
    }
}