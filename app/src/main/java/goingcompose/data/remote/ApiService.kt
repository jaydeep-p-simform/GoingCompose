package goingcompose.data.remote

import goingcompose.data.remote.apiresult.ApiResult
import goingcompose.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Provides remote APIs
 */
interface ApiService {
    @GET("?inc=name,location,picture,login&results=10&seed=abc")
    suspend fun loadUsers(@Query("page") page: Int): ApiResult<UserResponse>
}
