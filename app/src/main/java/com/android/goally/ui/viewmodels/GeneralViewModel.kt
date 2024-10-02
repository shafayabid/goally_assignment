package com.android.goally.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goally.data.db.entities.reminder.DailyRepeatValues
import com.android.goally.data.db.entities.reminder.Notification
import com.android.goally.data.db.entities.reminder.Reminder
import com.android.goally.data.db.entities.reminder.ReminderNotificationV2
import com.android.goally.data.db.entities.reminder.Schedule
import com.android.goally.data.db.entities.reminder.ScheduleV2
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.repo.GeneralRepo
import com.android.goally.util.LogUtil
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    private val generalRepo: GeneralRepo
) : ViewModel() {

    fun checkServerHealth(
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = generalRepo.checkHealth()) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    if (res.body?.status.equals("ok", true)) {
                        onSuccess("Server is up")
                    } else {
                        onError("Server is down")
                    }
                    onLoading(false)
                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                    onError(res.body?.message ?: "Server error")
                    onLoading(false)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Network error")
                    onLoading(false)
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Unknown error")
                    onLoading(false)
                }
            }
        }
    }

    fun getTokenFor(
        userEmail: String,
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = generalRepo.getToken(userEmail)) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    Log.d("TEMP_TOKEN", "response_body: ${res.body}")
                    res.body?.let {
                        if (!it.token.isNullOrEmpty() && !it.name.isNullOrEmpty()) {
                            //save token here which will be used for further api calls
                            withContext(Dispatchers.IO) {
                                generalRepo.insertAuthentication(
                                    Authentication(
                                        18,
                                        it.token,
                                        it.name!!
                                    )
                                )
                            }
                            getReminder(it.token!!)
                            onSuccess()
                        }
                    } ?: run {
                        onError("Something went wrong")
                    }
                    onLoading(false)
                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                    onError(res.body?.message ?: "Server error")
                    onLoading(false)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Network error")
                    onLoading(false)
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Unknown error")
                    onLoading(false)
                }
            }
        }
    }

    fun getReminder(auth: String) {
        viewModelScope.launch {
            when (val res = generalRepo.getReminders(auth)) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    Log.d("TEMP_REMINDER", "response_body: ${res.body}")
                    res.body?.let {
                        if (!it.isEmpty()) {
                            //save reminder here which will be used for displaying data
                            var mSchedule: Schedule
                            var mScheduleV2: ScheduleV2


                            withContext(Dispatchers.IO) {
                                res.body.forEach {
                                    mSchedule = Schedule(
                                        it.schedule.Sun,
                                        it.schedule.Mon,
                                        it.schedule.Tue,
                                        it.schedule.Wed,
                                        it.schedule.Thu,
                                        it.schedule.Fri,
                                        it.schedule.Sat
                                    )
                                    val mNotification = it.notifications.map { apiNotification ->
                                        convertApiNotificationToDb(apiNotification)
                                    }

                                    val mReminderNotification = it.reminderNotificationsV2.map { apiNotification ->
                                        convertReminderApiNotificationToDb(apiNotification)
                                    }

                                    mScheduleV2 = ScheduleV2(
                                        it.scheduleV2.timeType,
                                        it.scheduleV2.type,
                                        DailyRepeatValues(
                                            listOf(),
                                            listOf(),
                                            listOf(),
                                            listOf(),
                                            listOf(),
                                            listOf(),
                                            listOf(),
                                        )
                                    )

                                    generalRepo.insertReminder(
                                        Reminder(
                                            it._id,
                                            it.name,
                                            mSchedule,
                                            it.duration,
                                            it.visualAidUrl,
                                            mNotification,
                                            mReminderNotification,
                                            it.notifsV2SoundName,
                                            it.notifsV2SoundUrl,
                                            it.createdBy,
                                            it.clientId,
                                            it.ordering,
                                            it.libraryType,
                                            it.devices,
                                            it.entityType,
                                            it.ctaOrdering,
                                            it.type,
                                            mScheduleV2,
                                            it.limitRunPerDay,
                                            it.numberOfRunPerDay,
                                            it.limitRunPerHour,
                                            it.numberOfRunPerHour,
                                            it.narration,
                                            it.tags,
                                            it.enableVacationMode,
                                            it.createdAt,
                                            it.updatedAt,
                                            it.folder,
                                            it.folderId,
                                            it.label
                                        )
                                    )
                                }
                            }
                        }
                    } ?: run {

                    }

                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                }
            }
        }
    }

    private fun convertApiNotificationToDb(apiNotification: com.android.goally.data.model.api.response.reminder.Notification): Notification {
        return Notification(
            minutesBefore = apiNotification.minutesBefore,
            isReadReminderSoundEnabled = apiNotification.isReadReminderSoundEnabled,
            isPositiveReinfoSoundEnabled = apiNotification.isPositiveReinfoSoundEnabled,
            isNotificationSoundEnabled = apiNotification.isNotificationSoundEnabled,
            notificationSoundUrl = apiNotification.notificationSoundUrl // Assuming this can be safely mapped
        )
    }

    private fun convertReminderApiNotificationToDb(apiNotification: com.android.goally.data.model.api.response.reminder.ReminderNotificationsV2): ReminderNotificationV2 {
        return ReminderNotificationV2(
            minutesBefore = apiNotification.minutesBefore,
            notificationType = apiNotification.notificationType
        )
    }


    fun getAuthenticationLive() = generalRepo.getAuthenticationLive()
    fun getReminderLive() = generalRepo.getReminderLive()
    suspend fun getAuthentication() = generalRepo.getAuthentication()
}