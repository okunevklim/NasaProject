package com.example.nasatestproject.models

import java.io.Serializable

data class NasaResponse(
    val photos: ArrayList<NasaPost>?
) : Serializable