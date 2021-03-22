package com.example.nasatestproject.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasatestproject.models.NasaPostAsString
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface NasaPostsDao {
    @Query("SELECT * FROM nasa_posts")
    fun loadAllPosts(): Maybe<List<NasaPostAsString>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosts(posts: List<NasaPostAsString>?): Completable

    @Query("DELETE FROM nasa_posts")
    fun clearPostsDb(): Completable

    @Query("SELECT COUNT(*) FROM nasa_posts")
    fun getPostsCount(): Maybe<Int>
}
