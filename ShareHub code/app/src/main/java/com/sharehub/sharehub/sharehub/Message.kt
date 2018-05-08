package com.sharehub.sharehub.sharehub

import org.json.JSONObject

class Message {
    companion object {
        fun parseArray(json: JSONObject, name: String): Array<Message>? {
            val messages: MutableList<Message> = mutableListOf<Message>()
            val jsonarray = json.getJSONArray(name)
            if (jsonarray == null) {
                return null
            }
            for (i in 0 until jsonarray.length()) {
                messages.add(rawParse(jsonarray.getJSONObject(i)))
            }
            return messages.toTypedArray()
        }

        fun parse(rawjson: JSONObject, name: String): Message? {
            val json = rawjson.getJSONObject(name)
            if (json == null) {
                return null
            }
            return rawParse(json)
        }

        private fun rawParse(json: JSONObject): Message {
            val message = Message()
            message.author = json.cast("author")
            message.content = json.cast("content")
            message.objectId = json.cast("objectId")
            message.timeOfSending = json.cast("timeOfSending")
            return message
        }
    }

    var author: String? = null
    var timeOfSending: Int? = null
    var content: String? = null
    var objectId: String? = null

}