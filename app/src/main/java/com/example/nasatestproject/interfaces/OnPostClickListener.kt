package com.example.nasatestproject.interfaces

import com.example.nasatestproject.models.NasaPost

interface OnPostClickListener {
    fun onPostClick(nasaPost: NasaPost)
}