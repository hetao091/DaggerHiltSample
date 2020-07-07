package tt.reducto.daggerhiltsample.data.model

import com.squareup.moshi.JsonClass

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/2 17:36<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
@JsonClass(generateAdapter = true)
data class Result<out T : Any>(
    val code: Int,
    val data: T,
    val msg: String
)

@JsonClass(generateAdapter = true)
data class Joke(
    val limit: Int,
    val list: List<Repo>,
    val page: Int,
    val totalCount: Int,
    val totalPage: Int
)
@JsonClass(generateAdapter = true)
data class Repo(
    val content: String,
    val updateTime: String
)