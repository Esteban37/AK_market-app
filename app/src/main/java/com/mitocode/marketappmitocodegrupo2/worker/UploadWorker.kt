package com.mitocode.marketappmitocodegrupo2.worker

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.auth0.android.jwt.JWT
import com.mitocode.marketappmitocodegrupo2.data.repositories.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val sharedPreferences: SharedPreferences,
    private val userRepository: UserRepository
) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        val token = sharedPreferences.getString("TOKEN","") ?: ""
        val jwt = JWT(token)

        //Fecha y hora que expira mi token actual
        val expiresAt : Date? = jwt.expiresAt
        val expiresAtLong = expiresAt?.time

        //Si esta expirado el token
        //val isExpired = jwt.isExpired(10)


        val currenTimestam = System.currentTimeMillis()

        val diff = expiresAtLong!! - currenTimestam
        val seconds : Long = diff/1000
        val minuts = seconds / 60
        val hours = minuts / 60
        val days = hours / 24

        var condition = false
        if(hours <= 2){
            GlobalScope.launch {
                withContext(Dispatchers.IO){
                    condition = userRepository.refreshToken()
                }
            }

        }

        return if(condition) Result.success()
        else Result.failure()


    }
}
