package com.sharehub.sharehub.sharehub

import org.json.JSONArray
import org.json.JSONObject

inline fun <reified T> JSONObject.castArray(string: String): Array<T>? {
    if (this.isNull(string)) {
        return null
    }
    return try {
        val jsonArray = this[string] as JSONArray
        val list = ArrayList<T>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray[i] as T)
        }
        list.toTypedArray()
    }
    catch (e: Exception) {
        null
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> JSONObject.cast(string: String): T? {
    if (this.isNull(string)) {
        return null
    }
    return try {
        this[string] as T
    }
    catch (e: Exception) {
        null
    }
}