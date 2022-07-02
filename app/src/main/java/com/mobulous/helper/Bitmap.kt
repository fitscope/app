package com.mobulous.helper

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


fun Bitmap.convertToString(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}