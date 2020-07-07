package tt.reducto.daggerhiltsample.data.api


import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getJokes() = flow { emit(apiService.getJokes()) }

}