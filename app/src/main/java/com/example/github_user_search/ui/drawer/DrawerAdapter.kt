package com.example.github_user_search.ui.drawer

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.github_user_search.data.entity.User
import com.squareup.picasso.Picasso


class DrawerAdapter(
    var user: User
) : BaseObservable() {

    @Bindable
    fun getUserName(): String? {
        return user.googleFirstName + " " + user.googleLastName
    }

    @Bindable
    fun getUserEmail(): String? {
        return user.googleEmail
    }


    fun getImageUrl(): String? {
        return user.googleProfilePicUrl
    }

    companion object{
        @BindingAdapter("url")
        @JvmStatic
        fun loadImage(view: ImageView, url: String?) {
            Picasso.with(view?.context).load(url).into(view)
        }
    }



}