package com.mobulous.helper

fun String.capitalizeWords(): String = split("").map { it.capitalize() }.joinToString("")