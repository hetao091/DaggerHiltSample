package tt.reducto.daggerhiltsample.ui.jokes.viewModel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tt.reducto.daggerhiltsample.data.model.Joke
import tt.reducto.daggerhiltsample.data.model.Result
import tt.reducto.daggerhiltsample.data.repository.JokesRepository
import tt.reducto.daggerhiltsample.utils.NetWorkUtils
import tt.reducto.daggerhiltsample.utils.UIState
import javax.inject.Inject

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/2 18:55<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val netWorkUtils: NetWorkUtils,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val STATE_KEY = "state_handle"
    }

    private val _jokes  =  MutableLiveData<UIState<Result<Joke>>> ()
//        savedStateHandle.getLiveData(STATE_KEY)
    private val _network = MutableLiveData<Boolean>()


    // 对外提供
    val jokes: LiveData<UIState<Result<Joke>>>
        get() = _jokes

    //
    val networkState: LiveData<Boolean>
        get() = _network

    init {
        fetchJokes()
    }

    private fun fetchNetWorkState() {
        if (netWorkUtils.isNetworkConnected()) {
            _network.postValue(true)
        } else {
            _network.postValue(false)
        }
    }

    fun fetchJokes() {
        fetchNetWorkState()
        viewModelScope.launch {
            jokesRepository.getUsers().collect {
                _jokes.postValue(it)
            }

        }
    }

}