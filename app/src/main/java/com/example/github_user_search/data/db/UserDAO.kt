package com.example.github_user_search.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.github_user_search.data.entity.CURRENT_USER_ID
import com.example.github_user_search.data.entity.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User) : Long

    @Query("DELETE FROM user")
    fun remove()


    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun  getUser() : LiveData<User>

}