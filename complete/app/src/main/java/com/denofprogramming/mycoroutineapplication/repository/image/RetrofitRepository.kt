package com.denofprogramming.mycoroutineapplication.repository.image

import com.denofprogramming.mycoroutineapplication.network.NetworkService
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.domain.ImageDetail
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class RetrofitRepository(
    private val _networkService: NetworkService
) {

    private var _job = Job()

    fun cancel() {
        _job.cancel()
        _job = Job()
    }


    suspend fun fetchImage(): Resource<ImageDetail> = withContext(_job) {
        logMessage("Start fetchImage() downloading...")
        val response = _networkService.getImage()
        Resource.success(response.body())
    }


    companion object {

        fun build(networkService: NetworkService): RetrofitRepository {
            return RetrofitRepository(networkService)
        }
    }

}