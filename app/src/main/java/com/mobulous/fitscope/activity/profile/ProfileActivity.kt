package com.mobulous.fitscope.activity.profile

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.databinding.ActivityProfileBinding
import com.mobulous.fitscope.databinding.DialogDownloadingLayBinding
import com.mobulous.helper.*

class ProfileActivity : AppCompatActivity() {
    lateinit var bin: ActivityProfileBinding
    private var isGranted = false
    lateinit var broadcastManager: BroadcastReceiver
    private val pickerContent =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                Constants.isDefaultProfileSet = false
                //Constants.isProfileIsInUri = false

                PrefUtils.with(this).save(Enums.isProfileIsInUri.toString(), false)

                bin.imageView3.setImageBitmap(it)
                saveProfiepic(it)
            }
        }

    private val gallerpick = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let { uri ->
            Constants.isDefaultProfileSet = false
            PrefUtils.with(this).save(Enums.isProfileIsInUri.toString(), true)
            bin.imageView3.setImageURI(it)
            saveProfiepic(it)
        }

    }

    private var requestForPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permResult ->
            permResult.entries.forEach {
                isGranted = it.value
            }
            if (isGranted) {
                showProfilePicOption()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        listnr()
        // getDrawable(R.drawable.ic_group_152)?.toBitmap(100,100,null)


    }

    private fun saveProfiepic(pic: Any) {
        when (pic) {
            is Bitmap -> {
                PrefUtils.with(this).save(Enums.ProfileImage.toString(), pic.convertToString())
            }
            is Uri -> {
                PrefUtils.with(this).save(Enums.ProfileImage.toString(), pic.toString())
            }
        }
    }

    private fun initView() {
        bin.proileToolbar.ivDrawer.visibility = View.GONE
        bin.proileToolbar.ivback.visibility = View.VISIBLE
        bin.proileToolbar.ivMainNotification.visibility = View.GONE
        PrefUtils.with(this).apply {
            bin.tvProfileName.text = getString(Enums.UserName.toString(), "")
            bin.tvProfileEmail.text = getString(Enums.UserEmail.toString(), "Not logged in")
            bin.tvSignOutProfile.text = if (getString(
                    Enums.isLogin.toString(),
                    "false"
                ) == "true"
            ) "Sign Out" else "Sign In"
            if (!Constants.isDefaultProfileSet) {
                when (PrefUtils.with(this@ProfileActivity)
                    .getBoolean(Enums.isProfileIsInUri.toString(), false)) {
                    true -> {
                        bin.imageView3.setImageURI(
                            Uri.parse(
                                getString(Enums.ProfileImage.toString(), "")
                            )
                        )
                    }
                    false -> {
                        bin.imageView3.setImageBitmap(
                            getString(Enums.ProfileImage.toString(), "")?.convertToBitmap()
                        )
                    }
                }
            }

        }


    }

    private fun listnr() {
        bin.tvAddImgProfile.setOnClickListener {
            requestForPermission.launch(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
        bin.tvSignOutProfile.setOnClickListener {
            Constants.isSignOutFromInside = bin.tvSignOutProfile.text == "Sign Out"
            startActivity(
                Intent(this, BaseActivity::class.java).putExtra(
                    Constants.Data, "login"
                )
            )
        }
        bin.proileToolbar.ivback.setOnClickListener {
            onBackPressed()
        }
        bin.tvTermConditionProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TERMS_ND_CONDITION))
            startActivity(intent)
        }
        bin.tvPrivacyPolicyProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PRIVACY_URL))
            startActivity(intent)
        }
        bin.tvSupportProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SUPPORT_URL))
            startActivity(intent)
        }
        bin.tvDownlodQualityProfile.setOnClickListener {
            Dialog(this).apply {
                val view =
                    DialogDownloadingLayBinding.inflate(LayoutInflater.from(this@ProfileActivity))
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                setContentView(view.root)
                view.tvCancelDialogItem.setOnClickListener {
                    dismiss()
                }
                show()

            }
        }

        broadcastManager = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                intent?.let { recIntent ->
                    when (recIntent.getStringExtra(Constants.Type)) {
                        Enums.CameraIntent.toString() -> {
                            pickerContent.launch(null)
                        }
                        Enums.GalleryIntent.toString() -> {
                            gallerpick.launch(null)
                        }

                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastManager, IntentFilter(Constants.explicitBroadCastAction))

    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastManager)
    }
}