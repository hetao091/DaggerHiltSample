package tt.reducto.log

/**
 * Log 层操作顶层接口。
 *
 * <p>
 *     负责定义一系列对外提供的使用方法定义。
 * </p>
 *
 * <ul>
 *     <li></li>
 * </ul>
 *
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/25/20 13:50<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
interface Operator {
    /**
     * @param adapter
     */
    fun addAdapter(adapter: LogAdapter)

    fun t(tag: String?): Operator

    fun d(message: String, vararg args: Any)

    fun d(any: Any)

    fun e(message: String, vararg args: Any)

    fun e(throwable: Throwable, message: String, vararg args: Any)

    fun w(message: String, vararg args: Any)

    fun i(message: String, vararg args: Any)

    fun v(message: String, vararg args: Any)

    fun wtf(message: String, vararg args: Any)

    /**
     *
     */
    fun json(json: String?)

    /**
     *
     */
    fun xml(xml: String?)

    fun log(
        priority: Int,
        tag: String?,
        message: String?,
        throwable: Throwable?
    )

    fun clearLogAdapters()
}