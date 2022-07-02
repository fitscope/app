package com.mobulous.pojo.search

data class GetFilterParameterRes(
    val data: FilterParameterDataItem? = null,
    val message: String? = null,
    val status: Int? = null
)

data class FilterParameterDataItem(
    val filter: List<FilterItem?>? = null,
    val author: List<AuthorItem?>? = null
)

data class FilterItem(
    val createdAt: String? = null,
    val V: Int? = null,
    val id: String? = null,
    val sort: Int? = null,
    val value: List<String?>? = null,
    val key: String? = null,
    val updatedAt: String? = null

)

data class AuthorItem(
    val image: String? = null,
    val createdAt: String? = null,
    val V: Int? = null,
    val description: String? = null,
    val id: Int? = null,
    val title: String? = null,
    val updatedAt: String? = null
)

