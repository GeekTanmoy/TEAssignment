package com.example.techexactly.di


import android.text.TextUtils
import com.example.techexactly.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    
    private const val BASE_URL = "https://navkiraninfotech.com/g-mee-api/api/v1/"

    private const val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
    private const val TIMEOUT = 120

    @Singleton
    @Provides
    fun provideInterceptor() = Interceptor { chain ->
        val request = chain.request()

        var connectTimeout = chain.connectTimeoutMillis()
        var readTimeout = chain.readTimeoutMillis()
        var writeTimeout = chain.writeTimeoutMillis()

        val connectNew = request.header(CONNECT_TIMEOUT)
        val readNew = request.header(CONNECT_TIMEOUT)
        val writeNew = request.header(CONNECT_TIMEOUT)

        if (!TextUtils.isEmpty(connectNew)) {
            connectTimeout = connectNew!!.toInt()
        }
        if (!TextUtils.isEmpty(readNew)) {
            readTimeout = readNew!!.toInt()
        }
        if (!TextUtils.isEmpty(writeNew)) {
            writeTimeout = writeNew!!.toInt()
        }

        chain
            .withConnectTimeout(connectTimeout * 1000, TimeUnit.MILLISECONDS)
            .withReadTimeout(readTimeout * 1000, TimeUnit.MILLISECONDS)
            .withWriteTimeout(writeTimeout * 1000, TimeUnit.MILLISECONDS)
            .proceed(request)
    }


    @Singleton
    @Provides
    fun provideHttpClient(timeoutInterceptor:Interceptor):OkHttpClient{
        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            addInterceptor(timeoutInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun getInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providedApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}