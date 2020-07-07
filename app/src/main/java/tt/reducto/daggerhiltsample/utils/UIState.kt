package tt.reducto.daggerhiltsample.utils

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/2 18:19<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */

sealed class UIState<out T> {

    companion object {

        /**
         * 加载中。
         */
        fun <T> loading() = Loading<T>()

        /**
         *
         * @param data 返回数据
         */
        fun <T> success(data: T) = Success(data)

        /**
         *
         * @param msg  错误信息
         */
        fun <T> error(msg: String) = Error<T>(msg)


        /**
         *
         * @param msg String
         * @return Failure<T>
         */
        fun <T> failure(msg: String) = Failure<T>(msg)

    }

    class Loading<T> : UIState<T>()
    data class Success<T>(val data: T) : UIState<T>()
    //
    data class Error<T>(val msg: String) : UIState<T>()
    data class Failure<T>(val msg: String) : UIState<T>()

}