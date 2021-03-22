package com.example.nasatestproject.presenters

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.nasatestproject.R
import com.example.nasatestproject.base.NasaApplication
import com.example.nasatestproject.di.module.Api
import com.example.nasatestproject.models.NasaPost
import com.example.nasatestproject.models.NasaPostAsString
import com.example.nasatestproject.models.NasaResponse
import com.example.nasatestproject.utils.Constants.DEMO_KEY
import com.example.nasatestproject.viewmodels.RoomViewModel
import com.example.nasatestproject.views.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ListPresenter internal constructor() : MvpPresenter<ListView>() {
    private val TAG = ListPresenter::class.java.simpleName

    @Inject
    lateinit var api: Api

    init {
        NasaApplication.component.inject(this)
    }


    @SuppressLint("CheckResult")
    fun checkDatabase(
        roomViewModel: RoomViewModel,
        isConnected: Boolean,
        silentMode: Boolean
    ) {
        Log.i("NASA", "Checking database...")
        roomViewModel.getCountPosts()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it == 0) {
                    if (isConnected) {
                        Log.i("NASA", "Posts size is $it. Connected.")
                        getNasaPhotos(roomViewModel, silentMode)
                    }
                } else {
                    Log.i("NASA", "Posts size is $it. Connected. Loading from database.")
                    loadPostsFromDatabase(roomViewModel)
                    if (isConnected) {
                        Log.i("NASA", "Posts size is $it. Connected. Loading photos.")
                        getNasaPhotos(roomViewModel, silentMode)
                    }
                }
            }

    }

    @SuppressLint("CheckResult")
    fun loadPostsFromDatabase(roomViewModel: RoomViewModel) {
        Log.i("NASA", "Loading from database.")
        roomViewModel.loadAllPosts()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listPosts ->
                val nasaItems =
                    listPosts.map { api.gson.fromJson(it.content, NasaPost::class.java) }
                viewState?.handlePosts(nasaItems)
                Log.i("NASA", "Database Posts handled to view: $nasaItems")
            }
            ) {
                viewState?.showSnackBar(
                    R.string.db_error,
                    R.drawable.bg_rounded_error_snackbar,
                    R.drawable.ic_exclamation
                )
            }

    }

    @SuppressLint("CheckResult")
    fun getNasaPhotos(roomViewModel: RoomViewModel, silentMode: Boolean) {
        Log.i("NASA", "Getting nasa photos.")
        api.getService().getNasaPhotos(
            EARTH_DATE,
            PAGE_NUMBER,
            DEMO_KEY
        )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { nasaResponse: NasaResponse?, _: Throwable? ->
                val nasaPosts = nasaResponse?.photos
                Log.i("NASA", "Getting NASA photos... $nasaPosts")
                if (!nasaPosts.isNullOrEmpty()) {
                    viewState?.setNasaPhotos(nasaPosts)
                    val postsAsString =
                        nasaPosts.map { NasaPostAsString(it.id, api.gson.toJson(it)) }
                    roomViewModel.insertPosts(postsAsString)
                        .subscribeOn(Schedulers.computation())
                        .subscribe { loadPostsFromDatabase(roomViewModel) }

                } else {
                    if (!silentMode) {
                        viewState?.showAlertDialog(R.string.alert_msg)
                    }
                }
            }
    }

    companion object {
        const val PAGE_NUMBER: Long = 1
        const val EARTH_DATE = "2021-01-27"
    }
}