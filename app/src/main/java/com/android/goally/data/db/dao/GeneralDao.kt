package com.android.goally.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.goally.data.db.entities.reminder.Reminder
import com.android.goally.data.db.entities.token.Authentication


@Dao
interface GeneralDao {
    @Insert
    fun insertAuthentication(authentication: Authentication?)
    @Insert
    fun insertReminder(reminder: Reminder)
    @Query("Select * from authentication")
    fun getAuthenticationLive(): LiveData<Authentication?>
    @Query("Select * from reminder")
    fun getReminderLive(): LiveData<List<Reminder>?>
    @Query("SELECT * FROM reminder WHERE name = :nameValue")
    fun getReminderByName(nameValue: String): LiveData<Reminder>?
    @Query("Select * from authentication")
    suspend fun getAuthentication(): Authentication?
}