package com.mobulous.pojo.signUp

import com.google.gson.annotations.SerializedName

data class SignUpRes(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Auth(

	@field:SerializedName("token")
	val token: String? = null
)

data class User(

	@field:SerializedName("subscribed")
	val subscribed: Boolean? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: Any? = null,

	@field:SerializedName("custom_fields")
	val customFields: List<Any?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("auth")
	val auth: Auth? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
