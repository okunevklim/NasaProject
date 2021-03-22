package com.example.nasatestproject.di.component

import com.example.nasatestproject.di.module.Api
import com.example.nasatestproject.presenters.ListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Api::class])
interface ApiComponent {
    fun inject(listPresenter: ListPresenter)
}