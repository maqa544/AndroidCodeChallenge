package com.example.razorsyncdemo.dependencyInjection

import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun providesPokemonClient(retrofit: Retrofit) : PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

}