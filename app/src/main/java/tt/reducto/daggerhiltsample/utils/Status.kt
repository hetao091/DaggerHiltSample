package tt.reducto.daggerhiltsample.utils

/**
 * 状态。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;3/8/21 17:03<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2021, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
sealed class Status {

    data class FiveGStatus(
        val is5G: Boolean,
        val isMetered: Boolean
    ) : Status()

    /* 无权限 */
    object NoPermission : Status()
}
