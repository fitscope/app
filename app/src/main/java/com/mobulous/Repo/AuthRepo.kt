package com.mobulous.Repo

import com.mobulous.pojo.login.LoginRes
import com.mobulous.pojo.login.loginPost
import com.mobulous.pojo.signUp.SignUpRes
import com.mobulous.pojo.signUp.SignupPost
import com.mobulous.webservices.ApiInterface
import retrofit2.Response

class AuthRepo(private val mInterface: ApiInterface) {
    suspend fun createUser(model: SignupPost): Response<SignUpRes> {
        return mInterface.signup(model)
    }

    suspend fun hitLogin(loginPost: loginPost): Response<LoginRes> {
        return mInterface.login(loginPost)
    }
}