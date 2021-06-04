package tt.reducto.log

import tt.reducto.log.TTLog.Companion.ASSERT
import tt.reducto.log.TTLog.Companion.DEBUG
import tt.reducto.log.TTLog.Companion.ERROR
import tt.reducto.log.TTLog.Companion.INFO
import tt.reducto.log.TTLog.Companion.VERBOSE
import tt.reducto.log.TTLog.Companion.WARN
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException
import java.util.*

/**
 * TTLog 工具类。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/25/20 14:11<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
internal object Tools {

    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    
    fun logLevel(value: Int): String {
        return when (value) {
            VERBOSE -> "VERBOSE"
            DEBUG -> "DEBUG"
            INFO -> "INFO"
            WARN -> "WARN"
            ERROR -> "ERROR"
            ASSERT -> "ASSERT"
            else -> "UNKNOWN"
        }
    }


    /**
     *
     */
    fun toString(any: Any?): String {
        if (any == null) {
            return "null"
        }
        if (!any.javaClass.isArray) {
            return any.toString()
        }

        if (any is BooleanArray) {
            return Arrays.toString(any as BooleanArray?)
        }
        if (any is ByteArray) {
            return Arrays.toString(any as ByteArray?)
        }
        if (any is CharArray) {
            return Arrays.toString(any as CharArray?)
        }
        if (any is ShortArray) {
            return Arrays.toString(any as ShortArray?)
        }
        if (any is IntArray) {
            return Arrays.toString(any as IntArray?)
        }
        if (any is LongArray) {
            return Arrays.toString(any as LongArray?)
        }
        if (any is FloatArray) {
            return Arrays.toString(any as FloatArray?)
        }
        if (any is DoubleArray) {
            return Arrays.toString(any as DoubleArray?)
        }
        return if (any is Array<*>) {
            Arrays.deepToString(any as Array<Any?>?)
        } else "无法匹配到相对应的正确类型"
    }

}