package com.acuon.moengage.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.acuon.moengage.utils.extensions.dp
import com.acuon.moengage.utils.extensions.setImageRoundCornerCenterCrop
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        view.setImageRoundCornerCenterCrop(imageUrl)
    }

    @JvmStatic
    @BindingAdapter("imageURL", "cornerRadius", requireAll = false)
    fun loadImageWithRoundedCorner(view: ImageView, imageUrl: Any?, radius: Int = 0) {
        view.setImageRoundCornerCenterCrop(imageUrl, radius.dp)
    }

}