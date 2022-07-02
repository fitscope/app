package com.mobulous

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mobulous.fitscope.R

abstract class BaseAc<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initViews()
        observers()
        requestedOrientation =
            if (resources.getBoolean(R.bool.isTablet)) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        listnr()


    }

    abstract fun getViewBinding(): VB
    abstract fun listnr()
    abstract fun initViews()
    abstract fun observers()


}