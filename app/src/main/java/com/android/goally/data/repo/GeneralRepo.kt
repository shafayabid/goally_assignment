package com.android.goally.data.repo

import com.android.goally.data.db.dao.GeneralDao
import com.android.goally.data.db.entities.reminder.Reminder
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.network.rest.api.GeneralApi


class GeneralRepo(
    private val generalApi: GeneralApi,
    private val generalDao: GeneralDao,
) {

    suspend fun checkHealth() = generalApi.checkHealth()
    suspend fun getToken(userEmail:String) = generalApi.getToken(userEmail)
    suspend fun getReminders(auth:String) = generalApi.getReminders(auth)


    fun insertAuthentication(authentication: Authentication) = generalDao.insertAuthentication(authentication)
    fun insertReminder(reminder: Reminder) = generalDao.insertReminder(reminder)
    fun getAuthenticationLive() = generalDao.getAuthenticationLive()
    fun getReminderLive() = generalDao.getReminderLive()
    fun getReminderByName(name: String) = generalDao.getReminderByName(name)
    suspend fun getAuthentication() = generalDao.getAuthentication()

}