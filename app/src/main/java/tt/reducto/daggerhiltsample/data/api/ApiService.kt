package tt.reducto.daggerhiltsample.data.api


import retrofit2.Response
import retrofit2.http.GET
import tt.reducto.daggerhiltsample.data.model.Joke
import tt.reducto.daggerhiltsample.data.model.Result

interface ApiService {


    @GET("test.txt")
    suspend fun getJokes(): Result<Joke>

}