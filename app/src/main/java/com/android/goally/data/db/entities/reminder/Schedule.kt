package com.android.goally.data.db.entities.reminder

import androidx.room.ColumnInfo

data class Schedule(
    @ColumnInfo(name = "Sun") val sun: String?,
    @ColumnInfo(name = "Mon") val mon: String?,
    @ColumnInfo(name = "Tue") val tue: String?,
    @ColumnInfo(name = "Wed") val wed: String?,
    @ColumnInfo(name = "Thu") val thu: String?,
    @ColumnInfo(name = "Fri") val fri: String?,
    @ColumnInfo(name = "Sat") val sat: String?
)
