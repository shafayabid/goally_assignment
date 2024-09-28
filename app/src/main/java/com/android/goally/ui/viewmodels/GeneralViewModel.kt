package com.android.goally.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        onSuccess: (String) -> Unit) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = generalRepo.checkHealth()) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    if(res.body?.status.equals("ok", true)) {
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

    fun getTokenFor(userEmail:String,
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: () -> Unit) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = generalRepo.getToken(userEmail)) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    Log.d("TEMP_TOKEN", "response_body: ${res.body}")
                    res.body?.let {
                        if(!it.token.isNullOrEmpty() && !it.name.isNullOrEmpty()){
                            //save token here which will be used for further api calls
                            withContext(Dispatchers.IO){
                                generalRepo.insertAuthentication(Authentication(18, it.token, it.name!!))
                            }
                            getReminder(it.token!!)
                            onSuccess()
                        }
                    }?:run {
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
                            //save token here which will be used for further api calls

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

    fun getAuthenticationLive() = generalRepo.getAuthenticationLive()
    suspend fun getAuthentication() = generalRepo.getAuthentication()
}