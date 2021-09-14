package com.denofprogramming.mycoroutineapplication.ui.main

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.denofprogramming.mycoroutineapplication.R
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.domain.ImageDetail
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage


private fun applyImage(imgView: ImageView, model: ImageDetail?) {
    model?.let { imageDetail: ImageDetail ->
        imageDetail.message?.let { uri: String ->
            imgView.load(uri) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
                transformations(CircleCropTransformation())
            }
        }
    }
}

@BindingAdapter("android:liveDataImageBitmap")
fun ImageView.setLiveDataImageBitmap(item: Resource<ImageDetail>?) {
    logMessage("liveDataImageBitmap -> State: $item")
    item?.let {
        when {
            item.isNone() -> setImageResource(R.drawable.ic_broken_image)
            item.isLoading() -> setImageResource(R.drawable.loading_animation)
            item.isSuccess() -> applyImage(this, item.data)
            item.isError() -> setImageResource(R.drawable.ic_broken_image)
            else -> setImageResource(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("android:imageLoadStatus")
fun TextView.setImageLoadStatus(item: Resource<ImageDetail>?) {
    text = convertResourceToString(item, context.resources)
}


@BindingAdapter("android:loadingEnable")
fun Button.setLoadingEnable(item: Resource<ImageDetail>?) {
    isEnabled = convertResourceToBoolean(item)
}

@BindingAdapter("android:cancellingEnable")
fun Button.setCancellingEnable(item: Resource<ImageDetail>?) {
    isEnabled = true
}
