package com.example.androidtask.data.repository

import com.example.androidtask.presentation.ListItem
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomerDeserializer : JsonDeserializer<ListItem> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ListItem {
        val jsonObject = json.asJsonObject
        return if (jsonObject.has("thumbnail")) ListItem.VideoItem(
            thumbnail = jsonObject.get("thumbnail").asString,
            title = jsonObject.get("title").asString,
            datetime = jsonObject.get("datetime").asString,
            isBookmarked = jsonObject.get("isBookmarked").asBoolean
        )
        else ListItem.ImageItem(
            thumbnailUrl = jsonObject.get("thumbnailUrl").asString,
            siteName = jsonObject.get("siteName").asString,
            datetime = jsonObject.get("datetime").asString,
            isBookmarked = jsonObject.get("isBookmarked").asBoolean
        )
    }
}
