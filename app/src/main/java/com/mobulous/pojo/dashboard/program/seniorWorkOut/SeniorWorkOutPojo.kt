package com.mobulous.pojo.dashboard.program.seniorWorkOut

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.program.SeniorWorkOutChaptersItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SeniorWorkOutPojo(
    val parent_lbl: String,
    val parent_object: ArrayList<SeniorChildHorizontalDataItem>
) : Parcelable

@Parcelize
data class SeniorChildHorizontalDataItem(
    val image_url: String,
    val duration: String,
    val name: String,
    val nestedChildrens: @RawValue ArrayList<SeniorWorkOutChaptersItem?>
) : Parcelable


@Parcelize
data class ChaptersWeightLossItem(
    @field:SerializedName("enroll_image")
    val enrollImage: String? = null,

    @field:SerializedName("duration")
    val duration: Int? = null,

    @field:SerializedName("preview_image")
    val previewImage: String? = null,

    @field:SerializedName("in_favorites")
    val inFavorites: @RawValue Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("has_access")
    val hasAccess: Boolean? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("details_url")
    val detailsUrl: @RawValue Any? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("available_at")
    val availableAt: @RawValue Any? = null
) : Parcelable

