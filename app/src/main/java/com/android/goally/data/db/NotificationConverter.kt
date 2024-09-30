package com.android.goally.data.db

import androidx.room.TypeConverter
import com.android.goally.data.db.entities.reminder.Notification
import com.google.gson.Gson

class NotificationConverter {
    @TypeConverter
    fun fromNotificationList(list: List<Notification>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toNotificationList(data: String?): List<Notification>? {
        return data?.let { Gson().fromJson(it, Array<Notification>::class.java).toList() }
    }
}