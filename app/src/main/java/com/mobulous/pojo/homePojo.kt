package com.mobulous.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class homePojo(
    val parent_lbl: String,
    val parent_object: ArrayList<ChildHorizontalDataItem>
) : Parcelable

@Parcelize
data class ChildHorizontalDataItem(
    val image_url: String,
    val duration: String,
    val name: String,
    val id: String? = null
) : Parcelable