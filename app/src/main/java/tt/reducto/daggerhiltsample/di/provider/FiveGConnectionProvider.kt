package tt.reducto.daggerhiltsample.di.provider

import android.telephony.PhoneStateListener
import android.telephony.TelephonyDisplayInfo
import android.telephony.TelephonyManager
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
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;3/8/21 17:37<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2021, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
class FiveGConnectionProvider @Inject constructor(flow: Flow<Int?>) {

    private val fiveGTypes = listOf(
        TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_LTE_ADVANCED_PRO,
        TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_NSA,
        TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_NSA_MMWAVE
    )

    companion object {
        fun getFlow(telephonyManager: TelephonyManager) = callbackFlow {
            println("Starting 5G Status flow")
            val callback = object : PhoneStateListener() {
                override fun onDisplayInfoChanged(displayInfo: TelephonyDisplayInfo) {
                    try {
                        super.onDisplayInfoChanged(displayInfo)
                        TTLog.d("Emitting 5G Status: ${displayInfo.networkType}")
                        offer(displayInfo.networkType)
                    } catch (e: SecurityException) {
                        TTLog.d("Emitting 5G Status: <null>")
                        offer(null)
                    }
                }
            }
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    offer(telephonyManager.dataNetworkType)
                }
            } catch (e: SecurityException) {
                TTLog.d("Emitting 5G Status: <null>")
                offer(null)
            }
            telephonyManager.listen(callback, PhoneStateListener.LISTEN_DISPLAY_INFO_CHANGED)
            awaitClose { telephonyManager.listen(callback, PhoneStateListener.LISTEN_NONE) }
        }
    }

    val fiveGFlow = flow.map { it?.let { fiveGTypes.contains(it) } }
}