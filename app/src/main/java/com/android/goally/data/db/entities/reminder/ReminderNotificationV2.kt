package com.android.goally.data.db.entities.reminder

import com.google.gson.annotations.SerializedName

data class ReminderNotificationV2(
    @SerializedName("notificationType")
    val notificationType: String?,
    @SerializedName("minutesBefore")
    val minutesBefore: Int
)
