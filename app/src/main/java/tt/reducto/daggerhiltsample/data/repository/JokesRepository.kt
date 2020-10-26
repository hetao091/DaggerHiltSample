package tt.reducto.daggerhiltsample.data.repository

import kotlinx.coroutines.flow.*
import tt.reducto.daggerhiltsample.data.api.ApiHelper
import tt.reducto.daggerhiltsample.data.model.Joke
import tt.reducto.daggerhiltsample.data.model.Result
import tt.reducto.daggerhiltsample.utils.UIState
import javax.inject.Inject

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/2 18:11<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
class JokesRepository @Inject constructor(private val apiHelper: ApiHelper) {
//    suspend fun getUsers() = apiHelper.getJokes()

    suspend fun getUsers(): Flow<UIState<Result<Joke>>> = flow {
        //
        emit(UIState.loading())
        //
        apiHelper.getJokes()
            .catch { e ->
                emit(UIState.error<Result<Joke>>(e.toString()))
            }.onCompletion {
                //
            }.collect {
                if (it.code == 1) {
                    emit(UIState.success(it))
                } else {
                    emit(UIState.failure<Result<Joke>>(it.msg))
                }
            }
    }

}