package tt.reducto.log


/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/25/20 13:57<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */

interface LogAdapter {

    /**
     * 日志打印开关。
     *
     * @param priority
     * @param tag
     */
    fun isLoggable(priority: Int, tag: String?): Boolean


    /**
     * 暴露给 策略器打印方法。
     *
     * @param priority
     * @param tag
     * @param message
     */
    fun log(priority: Int, tag: String?, message: String)
}

class AndroidLogAdapter : LogAdapter {
    private val formatStrategy: FormatStrategy

    constructor() {
        formatStrategy = TTFormatStrategy.newBuilder().build()
    }

    /**
     * @param formatStrategy 策略配置
     */
    constructor(formatStrategy: FormatStrategy) {
        this.formatStrategy = formatStrategy
    }

    override fun isLoggable(priority: Int,  tag: String?): Boolean {
        return true
    }

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy.log(priority, tag, message)
    }
}
