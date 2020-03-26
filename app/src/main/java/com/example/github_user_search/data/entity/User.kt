package com.example.github_user_search.data.entity

import androidx.databinding.Bindable
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseMethod
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

const val CURRENT_USER_ID = 0

@Entity
data class User(
    val googleId : String? = null,
    val googleFirstName :String? = null,
    val googleLastName :String? = null,
    val googleEmail :String? = null,
    val googleProfilePicUrl :String? = null,
    val googleIdToken :String? = null
    ) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID

}