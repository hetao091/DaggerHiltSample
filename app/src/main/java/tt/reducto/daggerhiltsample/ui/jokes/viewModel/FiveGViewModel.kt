package tt.reducto.daggerhiltsample.ui.jokes.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import tt.reducto.daggerhiltsample.utils.Status
import tt.reducto.log.TTLog
import javax.inject.Inject
import javax.inject.Named

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;3/8/21 16:50<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2021, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
@HiltViewModel
class FiveGViewModel @Inject constructor(
    @Named("FiveG") fiveGFlow: Flow<Boolean?>,
    @Named("Metered") isMeteredFlow: Flow<Boolean>
) : ViewModel() {
    /* */

    val statusFlow = fiveGFlow.combine(isMeteredFlow) { connection, isMetered ->

        TTLog.d("接入5G状态: $connection, $isMetered")
        if (connection == null) {
            Status.NoPermission
        } else {
            Status.FiveGStatus(
                is5G = connection,
                isMetered = isMetered
            )
        }
    }

}