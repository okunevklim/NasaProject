package com.example.nasatestproject.views

import com.example.nasatestproject.models.NasaPost
import moxy.MvpView
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEnd

@AddToEnd
interface ListView : MvpView {
    fun setNasaPhotos(nasaPhotos: ArrayList<NasaPost>)
    fun showSnackBar(title: Int, backgroundColorID: Int, imageID: Int)
    fun showAlertDialog(messageID: Int)
    fun handlePosts(nasaPosts: List<NasaPost>)
}