package com.example.nasatestproject.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NasaCamera(
    val id: Long?,
    val name: String?,
    @SerializedName("rover_id")
    val roverID: Long?,
    @SerializedName("full_name")
    val cameraFullName: String?
) : Serializable