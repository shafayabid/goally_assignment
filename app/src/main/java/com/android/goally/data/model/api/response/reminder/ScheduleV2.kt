package com.android.goally.data.model.api.response.reminder

data class ScheduleV2(
    val dailyRepeatValues: DailyRepeatValues,
    val timeType: String,
    val timeValue: String,
    val type: String,
    val yearlyRepeatDateValue: String
)