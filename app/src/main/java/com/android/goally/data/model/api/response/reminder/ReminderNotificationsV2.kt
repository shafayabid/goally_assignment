package com.android.goally.data.model.api.response.reminder

data class ReminderNotificationsV2(
    val _id: String,
    val minutesBefore: Int,
    val notificationType: String
)