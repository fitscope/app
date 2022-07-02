package com.mobulous.base

import android.widget.Toast
import com.mobulous.fitscope.databinding.ActivityDemo2Binding

class demo : BaseActivity<ActivityDemo2Binding>() {
    override fun getActivityBinding(): ActivityDemo2Binding =
        ActivityDemo2Binding.inflate(layoutInflater)

    override fun initviews() {

    }
}