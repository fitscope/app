package com.mobulous.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

var timer: Timer? = null
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // user is typing: reset already started timer (if existing)
            timer?.cancel()
        }

        override fun afterTextChanged(editable: Editable?) {
            // user typed: start the timer
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    afterTextChanged.invoke(editable.toString())
                }
            }, 2000) // 600ms delay before the timer executes the „run“ method from TimerTask
        }
    })
}