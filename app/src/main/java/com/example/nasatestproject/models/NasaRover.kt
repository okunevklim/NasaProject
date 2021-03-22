package com.example.nasatestproject.models

import java.io.Serializable

data class NasaRover(
    val id: Long?,
    val name: String?,
    val status: String?
) : Serializable