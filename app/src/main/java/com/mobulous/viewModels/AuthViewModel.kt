package com.mobulous.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.AuthRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.login.LoginRes
import com.mobulous.pojo.login.loginPost
import com.mobulous.pojo.signUp.SignUpRes
import com.mobulous.pojo.signUp.SignupPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {

    private val _loginData = MutableLiveData<NetworkReponse<LoginRes>>()
    val loginData: LiveData<NetworkReponse<LoginRes>> get() = _loginData

    private val _signUpData = MutableLiveData<NetworkReponse<SignUpRes>>()
    val signUpData: LiveData<NetworkReponse<SignUpRes>> get() = _signUpData


    fun login(model: loginPost) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loginData.postValue(NetworkReponse.Success(repo.hitLogin(model).body()))
            } catch (e: Exception) {
                _loginData.postValue(NetworkReponse.Error(e.printStackTrace().toString()))
            }
        }
    }

    fun createUser(model: SignupPost) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _signUpData.postValue(NetworkReponse.Success(repo.createUser(model).body()))

            } catch (e: Exception) {
                _signUpData.postValue(NetworkReponse.Error(e.printStackTrace().toString()))
            }

        }
    }

}