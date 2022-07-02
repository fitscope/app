package com.mobulous.pojo

 data class filterPojo (
    val filterparent_lbl: String,
    val filterparent_object: ArrayList<FilterChildHorizontalDataItem>

)
data class FilterChildHorizontalDataItem(

    val filter_name: String,
    var isSelected:Boolean = false
)