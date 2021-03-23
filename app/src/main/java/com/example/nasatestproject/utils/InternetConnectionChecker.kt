package com.example.nasatestproject.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.example.nasatestproject.base.NasaApplication
import com.example.nasatestproject.network.NetworkConnection

object InternetConnectionChecker {
    fun checkInternetConnection(
        viewLifecycleOwner: LifecycleOwner,
        listener: (isConnected: Boolean) -> Unit
    ) {
        val networkConnection = NetworkConnection(NasaApplication.context)
        networkConnection.observe(viewLifecycleOwner, { isConnected ->
            listener.invoke(isConnected)
        }
        )
    }
}