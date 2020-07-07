package tt.reducto.daggerhiltsample.data.api



import kotlinx.coroutines.flow.Flow
import tt.reducto.daggerhiltsample.data.model.Joke
import tt.reducto.daggerhiltsample.data.model.Result

interface ApiHelper {

    suspend fun getJokes(): Flow<Result<Joke>>
}