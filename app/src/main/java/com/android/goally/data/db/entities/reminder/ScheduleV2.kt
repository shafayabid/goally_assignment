package com.android.goally.data.db.entities.reminder

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class ScheduleV2(
    @SerializedName("timeType")
    val timeType: String?,
    @SerializedName("type")
    val type: String?,
    @Embedded
    @SerializedName("dailyRepeatValues")
    val dailyRepeatValues: DailyRepeatValues?
)
