package tt.reducto.log


/**
 * 自定义log 主体 。
 *
 * <p>
 *     ......。
 * </p>
 *
 * <ul>
 *     <li></li>
 * </ul>
 *
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/25/20 14:09<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
class TTLog private constructor() {


    companion object {

        /* 日志分级 */
        const val VERBOSE = 2
        const val DEBUG = 3
        const val INFO = 4
        const val WARN = 5
        const val ERROR = 6
        const val ASSERT = 7

        /* 初始化属性 */
        private var printer: Operator = TTLogOperator()
        private var mDefaultAdapter = AndroidLogAdapter()

        init {
            printer.addAdapter(mDefaultAdapter)
        }

        /**
         * 添加自定义打印配置。
         */
        @JvmStatic
        fun printer(p: Operator?) {
            p?.let {
                printer = it
            }
        }

        /**
         * 增加自定义装饰器。
         */
        @JvmStatic
        fun addLogAdapter(adapter: LogAdapter?) {
            adapter?.let {
                printer.addAdapter(it)
            } ?: kotlin.run {
                printer.addAdapter(mDefaultAdapter)
            }
        }

        /**
         * 清楚装饰器。
         */
        @JvmStatic
        fun clearLogAdapters() {
            printer.clearLogAdapters()
        }

        @JvmStatic
        fun t(tag: String?): Operator? {
            return printer.t(tag)
        }

        /**
         * 这里自定义。
         */
        @JvmStatic
        fun log(
            priority: Int,
            tag: String?,
            message: String?,
            throwable: Throwable?
        ) {
            printer.log(priority, tag, message, throwable)
        }

        @JvmStatic
        fun d(message: String, vararg args: Any) {
            printer.d(message, args)
        }

        @JvmStatic
        fun d(any: Any?) {
            any?.let {
                printer.d(it)
            }
        }

        @JvmStatic
        fun e(message: String, vararg args: Any?) {
            printer.e(message, args)
        }

        @JvmStatic
        fun e(throwable: Throwable?, message: String, vararg args: Any?) {
            throwable?.let {
                printer.e(it, message, args)
            }
        }

        @JvmStatic
        fun i(message: String, vararg args: Any?) {
            printer.i(message, args)
        }

        @JvmStatic
        fun v(message: String, vararg args: Any?) {
            printer.v(message, args)
        }

        @JvmStatic
        fun w(message: String, vararg args: Any?) {
            printer.w(message, args)
        }

        /**
         *
         */
        @JvmStatic
        fun wtf(message: String, vararg args: Any?) {
            printer.wtf(message, args)
        }

        /**
         * 打印json.
         */
        @JvmStatic
        fun json(json: String?) {
            printer.json(json)
        }

        /**
         * 打印xml。
         */
        @JvmStatic
        fun xml(xml: String?) {
            printer.xml(xml)
        }
    }

}