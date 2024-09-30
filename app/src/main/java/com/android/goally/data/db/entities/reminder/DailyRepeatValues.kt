package com.android.goally.data.db.entities.reminder

import com.google.gson.annotations.SerializedName

data class DailyRepeatValues(
    @SerializedName("Mon") val mon: List<String>?,
    @SerializedName("Tue") val tue: List<String>?,
    @SerializedName("Wed") val wed: List<String>?,
    @SerializedName("Thu") val thu: List<String>?,
    @SerializedName("Fri") val fri: List<String>?,
    @SerializedName("Sat") val sat: List<String>?,
    @SerializedName("Sun") val sun: List<String>?
)
