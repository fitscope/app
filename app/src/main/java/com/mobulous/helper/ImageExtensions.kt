package com.mobulous.helper

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadNormalPhoto_Dimens300(url: Any?) {
    Glide.with(context).load(url)
        /*.apply(RequestOptions.overrideOf(Constants.width_300, Constants.height_300).fitCenter())*/
        .placeholder(context.GliderPlaceHolder())
        .into(this)

}

fun ImageView.loadNormalPhoto_Dimens400(url: Any?) {
    Glide.with(context).load(url)
        /*.apply(RequestOptions.overrideOf(Constants.width_400, Constants.height_400).fitCenter())*/
        .placeholder(context.GliderPlaceHolder())
        .into(this)

}

fun ImageView.loadNormalPhoto_Dimens600(url: Any?) {
    Glide.with(context).load(url)
        /*.apply(RequestOptions.overrideOf(Constants.width_600, Constants.height_600).fitCenter())*/
        .placeholder(context.GliderPlaceHolder())
        .into(this)

}