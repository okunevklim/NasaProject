package com.example.nasatestproject.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NasaPost(
    val id: Long?,
    @SerializedName("img_src")
    val imgSrc: String?,
    @SerializedName("earth_date")
    val earthDate: String?,
    val camera: NasaCamera?,
    val rover: NasaRover?
) : Serializable