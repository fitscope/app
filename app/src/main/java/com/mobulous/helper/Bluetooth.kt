package com.example.healthcarekotlin.Activity.bluetooth

import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.mobulous.fitscope.R
import com.mobulous.helper.DeviceListActivity
import java.util.*


/**
 * Main activity.
 *
 * @author Lorensius W. L. T <lorenz></lorenz>@londatiga.net>
 */
class Bluetooth : Activity() {
    private var mStatusTv: TextView? = null
    private var mActivateBtn: Button? = null
    private var mPairedBtn: Button? = null
    var gattCallback: BluetoothGattCallback? = null
    private var mScanBtn: Button? = null
    var gatt: BluetoothGatt? = null
    var i = 0
    var myDevice: BluetoothDevice? = null
    private var mProgressDlg: ProgressDialog? = null
    var HEART_RATE_SERVICE_UUID: UUID? = null
    var CLIENT_CHARACTERISTIC_CONFIG_UUID: UUID? = null
    var HEART_RATE_MEASUREMENT_CHAR_UUID: UUID? = null
    var HEART_RATE_CONTROL_POINT_CHAR_UUID: UUID? = null
    private var mDeviceList = ArrayList<BluetoothDevice?>()
    lateinit var mBluetoothAdapter: BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902)
        HEART_RATE_SERVICE_UUID = convertFromInteger(0x1801)
        HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A37)
        HEART_RATE_CONTROL_POINT_CHAR_UUID = convertFromInteger(0x2A39)
        mStatusTv = findViewById<View>(R.id.tv_status) as TextView
        mActivateBtn = findViewById<View>(R.id.btn_enable) as Button
        mPairedBtn = findViewById<View>(R.id.btn_view_paired) as Button
        mScanBtn = findViewById<View>(R.id.btn_scan) as Button
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        /** */
        val scanCallback = LeScanCallback { device, rssi, scanRecord ->
            if (device.name != null) {
                if (device.name == "STORM") {
                    myDevice = device
                    connect()
                    i = 1
                }
                Log.d("scann", device.name)
            }
        }
        mBluetoothAdapter.startLeScan(scanCallback)
        gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d("state:", "connected")
                    gatt.discoverServices()

                }
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?
            ) {
                super.onCharacteristicChanged(gatt, characteristic)
                if (characteristic != null) {
                    Log.d("*****OnChange******", characteristic.value.toString())
                }else{
                    Log.d("*****OnChange******", "characteristic.value.toString()")

                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
                Log.d("vallll",characteristic?.value.toString().plus("______"))
                val value = characteristic!!.getStringValue(0)

                with(characteristic) {
                    when (status) {
                        BluetoothGatt.GATT_SUCCESS -> {
                            Log.i("BluetoothGattCallback", "Read characteristic $uuid:\n${characteristic.value.toHexString()}")
                        }
                        BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                            Log.e("BluetoothGattCallback", "Read not permitted for $uuid!")
                        }
                        else -> {
                            Log.e("BluetoothGattCallback", "Characteristic read failed for $uuid, error: $status")
                        }
                    }
                }
            }



            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                super.onServicesDiscovered(gatt, status)
                Log.d("onServicesDis", "_____")
                if(status == BluetoothGatt.GATT_SUCCESS){
                    with(gatt) {
                        Log.w(
                            "BluetoothGattCallback",
                            "Discovered ${services.size} services for ${device.address}"
                        )
                        printGattTable() // See implementation just above this section
                        // Consider connection setup as complete here
                    }

                    val characteristic = gatt.getService(UUID.fromString("00000af4-0000-1000-8000-00805f9b34fb"))
                        .getCharacteristic(UUID.fromString("00000afa-0000-1000-8000-00805f9b34fb"))


                    gatt.readCharacteristic(characteristic)

                    for (descriptor in characteristic.descriptors) {
                        Log.e("TAG", "BluetoothGattDescriptor: " + descriptor.uuid.toString())
                    }

//                    gatt.setCharacteristicNotification(characteristic, true)
//
//                    val descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
//
//                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
//                    gatt.writeDescriptor(descriptor)

                }


            }

            override fun onDescriptorWrite(
                gatt: BluetoothGatt?,
                descriptor: BluetoothGattDescriptor?,
                status: Int
            ) {
                super.onDescriptorWrite(gatt, descriptor, status)

                val characteristic = gatt!!.getService(HEART_RATE_SERVICE_UUID)
                    .getCharacteristic(HEART_RATE_CONTROL_POINT_CHAR_UUID)
                characteristic.value = byteArrayOf(1, 1)
                gatt.writeCharacteristic(characteristic)
            }



        }
        /** */
        mProgressDlg = ProgressDialog(this)
        mProgressDlg!!.setMessage("Scanning...")
        mProgressDlg!!.setCancelable(false)
        mProgressDlg!!.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
            dialog.dismiss()
            mBluetoothAdapter.cancelDiscovery()
        }
        if (mBluetoothAdapter == null) {
            showUnsupported()
        } else {
            mPairedBtn!!.setOnClickListener {
                val pairedDevices = mBluetoothAdapter!!.bondedDevices
                if (pairedDevices == null || pairedDevices.size == 0) {
                    showToast("No Paired Devices Found")
                } else {
                    val list = ArrayList<BluetoothDevice>()
                    list.addAll(pairedDevices)
                    val intent = Intent(this@Bluetooth, DeviceListActivity::class.java)
                    intent.putParcelableArrayListExtra("device.list", list)
                    startActivity(intent)
                }
            }
            mScanBtn!!.setOnClickListener { mBluetoothAdapter.startDiscovery() }
            mActivateBtn!!.setOnClickListener {
                if (mBluetoothAdapter.isEnabled) {
                    mBluetoothAdapter.disable()
                    showDisabled()
                } else {
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(intent, 1000)
                }
            }
            if (mBluetoothAdapter!!.isEnabled) {
                showEnabled()
            } else {
                showDisabled()
            }
        }
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(mReceiver, filter)
    }

    private fun connect() {
        if (myDevice != null && i == 1) {
            gatt = myDevice!!.connectGatt(this, false, gattCallback)
        }
    }

    fun ByteArray.toHexString(): String =
        joinToString(separator = " ", prefix = "0x") { String.format("%02X", it) }

    public override fun onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering) {
                mBluetoothAdapter!!.cancelDiscovery()
            }
        }
        super.onPause()
    }

    private fun BluetoothGatt.printGattTable() {
        if (services.isEmpty()) {
            Log.i(
                "printGattTable",
                "No service and characteristic available, call discoverServices() first?"
            )
            return
        }
        services.forEach { service ->
            val characteristicsTable = service.characteristics.joinToString(
                separator = "\n|--",
                prefix = "|--"
            )

            { it.uuid.toString() }
            Log.i(
                "printGattTable",
                "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
            )

        }
    }

    public override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }

    private fun showEnabled() {
        mStatusTv!!.text = "Bluetooth is On"
        mStatusTv!!.setTextColor(Color.BLUE)
        mActivateBtn!!.text = "Disable"
        mActivateBtn!!.isEnabled = true
        mPairedBtn!!.isEnabled = true
        mScanBtn!!.isEnabled = true
    }

    private fun showDisabled() {
        mStatusTv!!.text = "Bluetooth is Off"
        mStatusTv!!.setTextColor(Color.RED)
        mActivateBtn!!.text = "Enable"
        mActivateBtn!!.isEnabled = true
        mPairedBtn!!.isEnabled = false
        mScanBtn!!.isEnabled = false
    }

    private fun showUnsupported() {
        mStatusTv!!.text = "Bluetooth is unsupported by this device"
        mActivateBtn!!.text = "Enable"
        mActivateBtn!!.isEnabled = false
        mPairedBtn!!.isEnabled = false
        mScanBtn!!.isEnabled = false
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothAdapter.ACTION_STATE_CHANGED == action) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                if (state == BluetoothAdapter.STATE_ON) {
                    showToast("Enabled")
                    showEnabled()
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED == action) {
                mDeviceList = ArrayList()
                mProgressDlg!!.show()
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                mProgressDlg!!.dismiss()
                val newIntent = Intent(this@Bluetooth, DeviceListActivity::class.java)
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList)
                startActivity(newIntent)
            } else if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<Parcelable>(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice?
                mDeviceList.add(device)
                showToast("Found device " + device!!.name)
            }
        }
    }

    fun convertFromInteger(i: Int): UUID {
        val MSB = 0x0000000000001000L
        val LSB = -0x7fffff7fa064cb05L
        val value = (i and -0x1).toLong()
        return UUID(MSB or (value shl 32), LSB)
    }
}