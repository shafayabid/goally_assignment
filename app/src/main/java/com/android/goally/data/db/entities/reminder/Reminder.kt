package com.android.goally.data.db.entities.reminder

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android.goally.data.db.DeviceConverter
import com.android.goally.data.db.NotificationConverter
import com.android.goally.data.db.ReminderNotificationConverter
import com.android.goally.data.db.TagConverter
import com.google.gson.annotations.SerializedName

@Entity
data class Reminder(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @Embedded(prefix = "schedule_")
    @SerializedName("schedule")
    val schedule: Schedule?,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("visualAidUrl")
    val visualAidUrl: String?,

    @TypeConverters(NotificationConverter::class)
    @SerializedName("notifications")
    val notifications: List<Notification>?,

    @TypeConverters(ReminderNotificationConverter::class)
    @SerializedName("reminderNotificationsV2")
    val reminderNotificationsV2: List<ReminderNotificationV2>?,

    @SerializedName("notifsV2SoundName")
    val notifsV2SoundName: String?,

    @SerializedName("notifsV2SoundUrl")
    val notifsV2SoundUrl: String?,

    @SerializedName("createdBy")
    val createdBy: String?,

    @SerializedName("clientId")
    val clientId: String?,

    @SerializedName("ordering")
    val ordering: Int,

    @SerializedName("libraryType")
    val libraryType: String?,

    @TypeConverters(DeviceConverter::class)
    @SerializedName("devices")
    val devices: List<String>?,

    @SerializedName("entityType")
    val entityType: String?,

    @SerializedName("ctaOrdering")
    val ctaOrdering: Int,

    @SerializedName("type")
    val type: String?,

    @Embedded(prefix = "scheduleV2_")
    @SerializedName("scheduleV2")
    val scheduleV2: ScheduleV2?,

    @SerializedName("limitRunPerDay")
    val limitRunPerDay: Boolean,

    @SerializedName("numberOfRunPerDay")
    val numberOfRunPerDay: Int,

    @SerializedName("limitRunPerHour")
    val limitRunPerHour: Boolean,

    @SerializedName("numberOfRunPerHour")
    val numberOfRunPerHour: Int,

    @SerializedName("narration")
    val narration: Boolean,

    @TypeConverters(TagConverter::class)
    @SerializedName("tags")
    val tags: List<String>?,

    @SerializedName("enableVacationMode")
    val enableVacationMode: Boolean,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("folder")
    val folder: String?,

    @SerializedName("folderId")
    val folderId: String?,

    @SerializedName("label")
    val label: String?
)
