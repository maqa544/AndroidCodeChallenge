package com.example.razorsyncdemo.dependencyInjection

import com.example.razorsyncdemo.repository.Repository
import com.example.razorsyncdemo.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindsRepository(repositoryImpl: RepositoryImpl): Repository
}