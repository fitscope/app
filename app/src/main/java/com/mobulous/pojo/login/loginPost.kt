package com.mobulous.pojo.login

data class loginPost(
    val email: String,
    val password: String,
    val deviceType: String = "android",
    val fcmToken: String
)