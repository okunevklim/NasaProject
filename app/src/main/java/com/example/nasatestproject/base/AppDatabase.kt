package com.example.nasatestproject.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nasatestproject.R
import com.example.nasatestproject.models.NasaPostAsString

@Database(entities = [NasaPostAsString::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postsDao(): NasaPostsDao

    companion object {
        private lateinit var instance: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if (!this::instance.isInitialized) {
                instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        context.getString(R.string.package_name)
                    ).build()
            }
            return instance
        }
    }
}
