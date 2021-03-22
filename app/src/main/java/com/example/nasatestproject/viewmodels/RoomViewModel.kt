package com.example.nasatestproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nasatestproject.base.AppDatabase
import com.example.nasatestproject.models.NasaPostAsString
import io.reactivex.Completable
import io.reactivex.Maybe

class RoomViewModel(application: Application) : AndroidViewModel(application) {
    private var database: AppDatabase = AppDatabase.getDatabase(application)

    fun insertPosts(ingredients: List<NasaPostAsString>): Completable {
        return database.postsDao().insertAllPosts(ingredients)
    }

    fun loadAllPosts(): Maybe<List<NasaPostAsString>> {
        return database.postsDao().loadAllPosts()
    }

    fun getCountPosts(): Maybe<Int> {
        return database.postsDao().getPostsCount()
    }

    fun clearPostsDb(): Completable {
        return database.postsDao().clearPostsDb()
    }
}
