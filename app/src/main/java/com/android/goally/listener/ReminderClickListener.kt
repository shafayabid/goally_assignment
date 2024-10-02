package com.android.goally.listener

import com.android.goally.data.db.entities.reminder.Reminder

interface ReminderClickListener {
    fun onReminderClick(reminder: Reminder, position: Int)
}