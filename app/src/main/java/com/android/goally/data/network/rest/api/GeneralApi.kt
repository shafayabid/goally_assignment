package com.android.goally.data.network.rest.api

import com.android.goally.constants.WebServiceConstant
import com.android.goally.data.model.api.ErrorResponse
import com.android.goally.data.model.api.response.health.ServerHealthApiResponse
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.model.api.response.reminder.Reminder
import com.android.goally.data.network.rest.Headers
import com.haroldadmin.cnradapter.NetworkResponse
import com.kinfolk.world.data.model.api.response.culture.TokenResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GeneralApi {
    @GET(value = WebServiceConstant.CHECK_HEALTH)
    suspend fun checkHealth(): NetworkResponse<ServerHealthApiResponse, ErrorResponse>

    @GET(value = WebServiceConstant.GET_TOKEN)
    suspend fun getToken(@Query("name") name:String): NetworkResponse<TokenResponse, ErrorResponse>

    @GET(value = WebServiceConstant.GET_REMINDERS)
    suspend fun getReminders(@Header("Authorization") auth:String): NetworkResponse<Reminder, ErrorResponse>
}

