package tt.reducto.daggerhiltsample.di.provider

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import tt.reducto.log.TTLog
import javax.inject.Inject

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;3/8/21 17:13<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2021, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */

class NetworkCapabilitiesProvider @Inject constructor(
    flow: Flow<NetworkCapabilities>
) {

    private val notMeteredTypes = listOf(
        NetworkCapabilities.NET_CAPABILITY_TEMPORARILY_NOT_METERED,
        NetworkCapabilities.NET_CAPABILITY_NOT_METERED
    )

    companion object {
        @ExperimentalCoroutinesApi
        fun getFlow(connectivityManager: ConnectivityManager) = callbackFlow<NetworkCapabilities> {
            println("Starting Capabilities Status flow")
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    TTLog.d("Emitting Capabilities Status: $networkCapabilities")
                    offer(networkCapabilities)
                }
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(callback)
            } else {

            }

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
    }

    val isMeteredFlow = flow.map { notMeteredTypes.none(it::hasCapability) }
}