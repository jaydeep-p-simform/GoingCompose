package goingcompose.di

import android.content.Context
import goingcompose.BuildConfig
import goingcompose.data.remote.ApiService
import goingcompose.data.remote.apiresult.ApiResultCallAdapterFactory
import goingcompose.utils.Urls
import goingcompose.utils.pref.FlavorPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val HTTP_LOGGING_INTERCEPTOR: String = "HTTP_LOGGING_INTERCEPTOR"
private const val OKHTTP_CLIENT = "OKHTTP_CLIENT"
private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB
private const val NETWORK_CALL_TIMEOUT: Long = 1 // Minute

/**
 * Provides remote APIs dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    @Named(HTTP_LOGGING_INTERCEPTOR)
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Named(OKHTTP_CLIENT)
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        @Named(HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, CACHE_SIZE))
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.MINUTES)
        .writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.MINUTES)
        .build()

    @Provides
    fun provideRetrofit(
        @Named(OKHTTP_CLIENT) okHttpClient: OkHttpClient,
        flavorPreferences: FlavorPreferences,
        apiResultCallAdapterFactory: ApiResultCallAdapterFactory
    ): ApiService = Retrofit.Builder()
        .baseUrl(
            Urls.getBaseUrl(flavorPreferences.flavor)
                .also { Timber.d("Setting Base URL : $it") }
        )
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(apiResultCallAdapterFactory)
        .build()
        .create(ApiService::class.java)
}
