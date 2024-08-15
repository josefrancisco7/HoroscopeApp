package com.fran.horoscapp.data.network

import com.fran.horoscapp.BuildConfig.BASE_URL
import com.fran.horoscapp.data.RepositoryImpl
import com.fran.horoscapp.data.core.interceptors.AuthInterceptor
import com.fran.horoscapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient : OkHttpClient):Retrofit{
          return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{

        val interceptor=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideHoroscopeApiService(retrofit: Retrofit):HorosocopeApiService{
       return retrofit.create(HorosocopeApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: HorosocopeApiService):Repository{
        return RepositoryImpl(apiService)
    }

}