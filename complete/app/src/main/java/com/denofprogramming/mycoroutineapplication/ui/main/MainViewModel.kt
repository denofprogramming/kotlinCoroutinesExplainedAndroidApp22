package com.denofprogramming.mycoroutineapplication.ui.main


import androidx.lifecycle.*
import com.denofprogramming.mycoroutineapplication.network.NetworkServiceApi
import com.denofprogramming.mycoroutineapplication.repository.image.RetrofitRepository
import com.denofprogramming.mycoroutineapplication.repository.time.DefaultClock
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.domain.ImageDetail
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {


    private val _clock = DefaultClock.build()

    private val _imageRepository =
        RetrofitRepository.build(NetworkServiceApi.retrofitService)

    val image: LiveData<Resource<ImageDetail>> get() = _image

    private val _image = MutableLiveData<Resource<ImageDetail>>()

    val currentTimeTransformed = _clock.time.switchMap {
        val timeFormatted = MutableLiveData<String>()
        val time = _clock.timeStampToTime(it)
        logMessage("currentTimeTransformed time is $time")
        timeFormatted.value = time
        timeFormatted
    }

    init {
        startClock()
    }

    fun onButtonClicked() {
        logMessage("Start onButtonClicked()")
        viewModelScope.launch {
            loadImage()
        }

    }

    fun onCancelClicked() {
        _imageRepository.cancel()
    }

    private suspend fun loadImage() {
        logMessage("Start loadImage()")
        val imageResource = try {
            _imageRepository.fetchImage()
        } catch (e: Exception) {
            logMessage("loadImage() exception $e")
            Resource.error(e.localizedMessage ?: "No Message")
        }
        showImage(imageResource)
    }

    private fun showImage(imageResource: Resource<ImageDetail>) {
        logMessage("Start showImage()")
        _image.postValue(imageResource)
        logMessage("End showImage()")
    }

    private fun startClock() {
        logMessage("Start startClock()")
        _clock.start()
    }
}