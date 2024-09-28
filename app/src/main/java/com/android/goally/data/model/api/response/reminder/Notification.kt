package com.android.goally.data.model.api.response.reminder

data class Notification(
    val _id: String,
    val isNotificationSoundEnabled: Boolean,
    val isPositiveReinfoSoundEnabled: Boolean,
    val isReadReminderSoundEnabled: Boolean,
    val minutesBefore: Int,
    val notificationSoundUrl: String
)