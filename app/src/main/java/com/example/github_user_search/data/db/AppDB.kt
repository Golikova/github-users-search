package com.example.github_user_search.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.github_user_search.data.entity.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDB: RoomDatabase() {

    abstract fun getUserDao() :UserDAO

    companion object{

        private  var instance: AppDB? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) =
            instance ?: synchronized(LOCK) {
                instance ?: buildDB(context)
                    .also { instance = it }
            }

        private  fun buildDB (context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                "AppDB.db"
            ).allowMainThreadQueries()
                .build()
    }

}