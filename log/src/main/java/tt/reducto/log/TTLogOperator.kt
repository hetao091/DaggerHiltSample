package tt.reducto.log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import tt.reducto.log.TTLog.Companion.ASSERT
import tt.reducto.log.TTLog.Companion.DEBUG
import tt.reducto.log.TTLog.Companion.ERROR
import tt.reducto.log.TTLog.Companion.INFO
import tt.reducto.log.TTLog.Companion.VERBOSE
import tt.reducto.log.TTLog.Companion.WARN
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.*
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


/**
 * 默认打印格式配置 。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;12/25/20 14:01<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
internal class TTLogOperator : Operator {

    /* 缩进距离 */
    private val JSON_INDENT = 2

    /**
     * 根据 线程信息 保存 TAG 。
     */
    private val localTag = ThreadLocal<String>()

    private val logAdapters: MutableList<LogAdapter> = mutableListOf()

    override fun addAdapter(adapter: LogAdapter) {
        logAdapters.add((adapter))
    }

    override fun t(tag: String?): Operator {
        tag?.let {
            localTag.set(it)
        }

        return this
    }

    override fun d(message: String, vararg args: Any) {
        tLog(DEBUG, null, message, args)
    }

    override fun d(any: Any) {
        tLog(DEBUG, null, Tools.toString(any), null)
    }

    override fun e(message: String, vararg args: Any) {
        tLog(ERROR, null, message, args)
    }

    override fun e(throwable: Throwable, message: String, vararg args: Any) {
        tLog(ERROR, throwable, message, args)
    }

    override fun w(message: String, vararg args: Any) {
        tLog(WARN, null, message, args)
    }

    override fun i(message: String, vararg args: Any) {
        tLog(INFO, null, message, args)
    }

    override fun v(message: String, vararg args: Any) {
        tLog(VERBOSE, null, message, args)
    }

    override fun wtf(message: String, vararg args: Any) {
        tLog(ASSERT, null, message, args)
    }

    override fun json(json: String?) {
        if (json.isNullOrEmpty()) {
            d(" json 是空的！")
            return
        }

        try {
            val j = json.trim()
            if (j.startsWith("{")) {
                val jsonObject = JSONObject(j)
                val message: String = jsonObject.toString(JSON_INDENT)
                d(message)
                return
            }
            if (j.startsWith("[")) {
                val jsonArray = JSONArray(j)
                val message: String = jsonArray.toString(JSON_INDENT)
                d(message)
                return
            }
            e("Json 格式不合法！")
        } catch (e: JSONException) {
            e("Json 格式不合法！")
        }
    }

    override fun xml(xml: String?) {
        if (xml.isNullOrEmpty()) {
            d(" xml 是空的！")
            return
        }

        /* XML 解析 */
        try {
            val xmlInput: Source = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            d(xmlOutput.writer.toString().replaceFirst(">", ">\n"))
        } catch (e: TransformerException) {
            e(" xml 解析错误 ")
        }
    }

    /**
     * 清除Adapter。
     */
    override fun clearLogAdapters() {
        logAdapters.clear()
    }

    @Synchronized
    override fun log(
        priority: Int,
        tag: String?,
        message: String?,
        throwable: Throwable?
    ) {
        /* 定义打印体规则 */
        var msg = message
        throwable?.let {
            msg?.apply {
                if (this.isEmpty()) {
                    msg = "-----请添加打印信息-----"
                } else {
                    msg += " : " + Tools.getStackTraceString(it)
                }
            } ?: kotlin.run {
                msg = Tools.getStackTraceString(it)
            }
        }

        logAdapters.forEach {
            if (it.isLoggable(priority, tag)) {
                it.log(priority, tag, msg!!)
            }
        }
    }

    @Synchronized
    private fun tLog(
        priority: Int,
        throwable: Throwable?,
        msg: String,
        vararg args: Any?
    ) {
        val tag = getTag()
        val message = createMessage(msg, args)
        log(priority, tag, message, throwable)
    }

    /**
     * 格式化打印信息。
     */
    private fun createMessage(msg: String?, vararg args: Any?): String {
        return msg?.run {
            if (args.isNullOrEmpty()) {
                this
            } else {
                try {
                    String.format(this, *args)
                } catch (e: Exception) {
                    this
                }
            }
        } ?: kotlin.run {
            "-----请添加打印信息-----"
        }
    }

    /**
     * 获取TAG。
     */
    private fun getTag(): String? {
        val tag = localTag.get()
        return tag?.run {
            localTag.remove()
            this
        } ?: run {
            null
        }
    }
}