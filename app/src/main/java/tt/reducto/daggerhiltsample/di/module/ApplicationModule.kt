package tt.reducto.daggerhiltsample.di.module


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tt.reducto.daggerhiltsample.BuildConfig
import tt.reducto.daggerhiltsample.data.api.ApiHelper
import tt.reducto.daggerhiltsample.data.api.ApiHelperImpl
import tt.reducto.daggerhiltsample.data.api.ApiService
import tt.reducto.log.TTLog
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            private val mMessage = StringBuilder()
            override fun log(message: String) {
                // 请求或者响应开始
                if (message.startsWith("--> ")) {
                    mMessage.setLength(0)
                }
                mMessage.append(message+"\n")
                // 响应结束，打印整条日志
                if (message.startsWith("<-- END HTTP")) {
                    TTLog.d(mMessage.toString())
                }
            }

        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://cdn.jsdelivr.net/gh/hetao091/md_pic@master/compose/")
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}