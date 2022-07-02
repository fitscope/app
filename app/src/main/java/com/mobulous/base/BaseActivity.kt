package com.mobulous.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    protected lateinit var mViewBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getActivityBinding()
        setContentView(mViewBinding.root)
        initviews()
    }


    abstract fun getActivityBinding(): B

    abstract fun initviews()

}