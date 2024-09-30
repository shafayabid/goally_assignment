package com.android.goally.data.db.entities.reminder

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("minutesBefore")
    val minutesBefore: Int,
    @SerializedName("isReadReminderSoundEnabled")
    val isReadReminderSoundEnabled: Boolean,
    @SerializedName("isPositiveReinfoSoundEnabled")
    val isPositiveReinfoSoundEnabled: Boolean,
    @SerializedName("isNotificationSoundEnabled")
    val isNotificationSoundEnabled: Boolean,
    @SerializedName("notificationSoundUrl")
    val notificationSoundUrl: String?
)
