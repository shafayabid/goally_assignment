package com.android.goally.data.db

import androidx.room.TypeConverter
import com.android.goally.data.db.entities.reminder.ReminderNotificationV2
import com.google.gson.Gson

class ReminderNotificationConverter {
    @TypeConverter
    fun fromReminderNotificationList(list: List<ReminderNotificationV2>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toReminderNotificationList(data: String?): List<ReminderNotificationV2>? {
        return data?.let { Gson().fromJson(it, Array<ReminderNotificationV2>::class.java).toList() }
    }
}