package tt.reducto.log

import android.util.Log


/**
 * 打印规则默认配置 。
 *
 * <p>
 *     这里采用建造模式对外提供属性定制，处于对java调用兼容
 *     如果是kotlin 可以直接采用 《具名可选参数》
 * </p>
 *
 * <ul>
 *     <li></li>
 * </ul>
 *
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/25/20 15:39<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
class TTFormatStrategy private constructor(builder: Builder) : FormatStrategy {

    /* 日志缓冲区大小 */
    private val CHUNK_SIZE = 4000

    /* 打印栈长度 */
    private val MIN_STACK_OFFSET = 5
    /* 左上角 */
    private val TOP_LEFT_CORNER = '┌'
    /* 底下角 */
    private val BOTTOM_LEFT_CORNER = '└'
    /* 分割 */
    private val MIDDLE_CORNER = '├'
    /* */
    private val HORIZONTAL_LINE = '│'
    /* */
    private val DOUBLE_DIVIDER = "────────────────────────────────────────────────────────"
    private val SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
    private val TOP_BORDER = TOP_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private val MIDDLE_BORDER = MIDDLE_CORNER.toString() + SINGLE_DIVIDER + SINGLE_DIVIDER

    // 对外提供方法
    /* 控制打印多少个调用栈中的方法。默认 2 */
    private var methodCount = 0
    /* 控制偏移（隐藏）多少个方法后再开始打印方法。默认 5 */
    private var methodOffset = 0
    /*是否打印线程信息*/
    private var showThreadInfo = false
    /* 修改日志打印策略  */
    private var logStrategy: LogStrategy? = null
    /* TAG */
    private var tag: String? = null

    init {
        methodCount = builder.methodCount
        methodOffset = builder.methodOffset
        showThreadInfo = builder.showThreadInfo
        logStrategy = builder.logStrategy
        tag = builder.tag
    }

    companion object{
        fun newBuilder(): Builder {
            return Builder()
        }
    }


    override fun log(priority: Int, tag: String?, message: String?) {
        val mTag = formatTag(tag)
        logTopBorder(priority, mTag)
        logHeaderContent(priority, mTag, methodCount)
        message?.let {
            val bytes = it.toByteArray()
            val length = bytes.size
            if (length <= CHUNK_SIZE) {
                if (methodCount > 0) {
                    logDivider(priority, mTag)
                }
                logContent(priority, mTag, it)
                logBottomBorder(priority, mTag)
                return
            }
            if (methodCount > 0) {
                logDivider(priority, mTag)
            }
            var i = 0
            while (i < length) {
                val count = (length - i).coerceAtMost(CHUNK_SIZE)
                logContent(priority, mTag, String(bytes, i, count))
                i += CHUNK_SIZE
            }
            logBottomBorder(priority, mTag)
        }

    }

    /**
     * 添加顶部隔离边框。
     */
    private fun logTopBorder(logType: Int, tag: String?) {
        logChunk(logType, tag, TOP_BORDER)
    }

    /**
     * 打印头部线程 信息
     */
    private fun logHeaderContent(logType: Int, tag: String?, methodCount: Int) {
        var mMethodCount = methodCount
        val trace = Thread.currentThread().stackTrace
        if (showThreadInfo) {
            logChunk(
                logType,
                tag,
                HORIZONTAL_LINE.toString() + " Thread: " + Thread.currentThread().name
            )
            logDivider(logType, tag)
        }
        var level = ""
        val stackOffset = getStackOffset(trace) + methodOffset

        if (mMethodCount + stackOffset > trace.size) {
            mMethodCount = trace.size - stackOffset - 1
        }
        for (i in mMethodCount downTo 1) {
            val stackIndex = i + stackOffset
            if (stackIndex >= trace.size) {
                continue
            }
            val builder = StringBuilder()
            builder.append(HORIZONTAL_LINE)
                .append(' ')
                .append(level)
                .append(getSimpleClassName(trace[stackIndex].className))
                .append(".")
                .append(trace[stackIndex].methodName)
                .append(" ")
                .append(" (")
                .append(trace[stackIndex].fileName)
                .append(":")
                .append(trace[stackIndex].lineNumber)
                .append(")")
            level += "   "
            logChunk(logType, tag, builder.toString())
        }
    }

    private fun logBottomBorder(logType: Int, tag: String?) {
        logChunk(logType, tag, BOTTOM_BORDER)
    }

    /**
     * 打印分割线。
     */
    private fun logDivider(logType: Int, tag: String?) {
        logChunk(logType, tag, MIDDLE_BORDER)
    }

    /**
     * 打印内容。
     */
    private fun logContent(logType: Int, tag: String?, chunk: String) {
        val lines = chunk.split(System.getProperty("line.separator")!!).toTypedArray()
        for (line in lines) {
            logChunk(logType, tag, "$HORIZONTAL_LINE $line")
        }
    }

    private fun logChunk(priority: Int, tag: String?, chunk: String) {
        logStrategy?.log(priority, tag, chunk)
    }

    private fun getSimpleClassName(name: String): String {
        val lastIndex = name.lastIndexOf(".")
        return name.substring(lastIndex + 1)
    }

    /**
     *
     */
    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        while (i < trace.size) {
            val e = trace[i]
            val name = e.className
            if (name != TTLogOperator::class.java.name && name != TTLog::class.java.name) {
                return --i
            }
            i++
        }
        return -1
    }


    private fun formatTag(tag: String?): String? {
        return if (!tag.isNullOrEmpty() && (this.tag === tag)) {
            this.tag + "-" + tag
        } else this.tag
    }

    class Builder {
        var methodCount = 2
        var methodOffset = 0
        var showThreadInfo = true
        var logStrategy: LogStrategy? = null
        /* 默认TAG */
        var tag = "TT"

        fun methodCount(`val`: Int): Builder {
            methodCount = `val`
            return this
        }


        fun methodOffset(`val`: Int): Builder {
            methodOffset = `val`
            return this
        }


        fun showThreadInfo(`val`: Boolean): Builder {
            showThreadInfo = `val`
            return this
        }


        fun logStrategy(`val`: LogStrategy?): Builder {
            logStrategy = `val`
            return this
        }


        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun build(): TTFormatStrategy {
            if (logStrategy == null) {
                logStrategy = LogcatLogStrategy()
            }
            return TTFormatStrategy(this)
        }
    }
}

interface LogStrategy {
    fun log(priority: Int, tag: String?, message: String?)
}


/**
 * 替代策略配置。
 */
internal class LogcatLogStrategy : LogStrategy {

    override fun log(priority: Int, tag: String?, message: String?) {

        var t = tag
        /* 默认 TAG */
        if (t == null) {
            t = DEFAULT_TAG
        }
        /* 打印体 */
        message?.let {
            Log.println(priority, t, it)
        }


    }

    companion object {
        const val DEFAULT_TAG = "NO_TAG"
    }

}
