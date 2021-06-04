package tt.reducto.daggerhiltsample.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tt.reducto.daggerhiltsample.di.provider.FiveGConnectionProvider
import tt.reducto.daggerhiltsample.di.provider.NetworkCapabilitiesProvider
import javax.inject.Named

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;3/8/21 17:41<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2021, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
@Module
@InstallIn(SingletonComponent::class)
class FiveGModule {

    @Provides
    fun providesTelephonyManager(@ApplicationContext context: Context): TelephonyManager =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            context.getSystemService(TelephonyManager::class.java)
        } else {
            TODO("VERSION.SDK_INT < M")
        }

    @Provides
    fun providesTelephonyManagerFlow(telephonyManager: TelephonyManager) =
        FiveGConnectionProvider.getFlow(telephonyManager)

    @Provides
    @Named("FiveG")
    fun providesFiveGFlow(provider: FiveGConnectionProvider) =
        provider.fiveGFlow

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            context.getSystemService(ConnectivityManager::class.java)
        } else {
            TODO("VERSION.SDK_INT < M")
        }

    @Provides
    fun providesNetworkFlow(connectivityManager: ConnectivityManager) =
        NetworkCapabilitiesProvider.getFlow(connectivityManager)

    @Provides
    @Named("Metered")
    fun providesIsMeteredFlow(provider: NetworkCapabilitiesProvider) =
        provider.isMeteredFlow
}
