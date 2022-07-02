package com.mobulous.webservices

import android.content.Context
import com.mobulous.helper.Enums
import com.mobulous.helper.PrefUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceBuilder {
    private lateinit var application: Context
    fun getRetroClientBuilderToken(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.connectTimeout(200, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader(
                        "X-Store-Token", "+/0ykksCgV8f2w=="
                    )
                    .build()
                return chain.proceed(request)
            }
        })
        /*PrefUtils.with(application)
                            .getString(
                                Enums.Token.toString().plus(" "),
                                ""
                            )*/
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder
    }

    fun getRetroClientBuilderToken2(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.connectTimeout(200, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader(
                        "X-Store-Token", "+/0ykksCgV8f2w=="
                    ).addHeader(
                        "Authorization",
                        PrefUtils.with(application).getString(Enums.UserToken.toString(), "") ?: ""
                    )
                    .build()
                return chain.proceed(request)
            }
        })
        /*PrefUtils.with(application)
                            .getString(
                                Enums.Token.toString().plus(" "),
                                ""
                            )*/
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder
    }

    fun getRetroClientBuilder(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.connectTimeout(200, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        /*PrefUtils.with(application)
                            .getString(
                                Enums.Token.toString().plus(" "),
                                ""
                            )*/
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder
    }

    private val retrofitToken = Retrofit.Builder()
        .baseUrl(ApiConstants.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getRetroClientBuilderToken().build())
        .build()

    private val retrofitToken2 = Retrofit.Builder()
        .baseUrl(ApiConstants.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getRetroClientBuilderToken2().build())
        .build()


    private val mobulousRetrofitToken = Retrofit.Builder()
        .baseUrl(ApiConstants.ProgramBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getRetroClientBuilder().build())  //getRetroClientBuilderToken
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getRetroClientBuilder().build())
        .build()

    fun <T> buildService(service: Class<T>, context: Context): T {
        application = context
        return retrofit.create(service)
    }

    fun <T> buildServiceToken(service: Class<T>, context: Context): T {
        application = context
        return retrofitToken.create(service)
    }

    fun <T> buildServiceTokenAuthorisedWithStoreToken(service: Class<T>, context: Context): T {
        application = context
        return retrofitToken2.create(service)
    }


    fun <T> mobulousBuildServiceToken(service: Class<T>, context: Context): T {
        application = context
        return mobulousRetrofitToken.create(service)
    }


}