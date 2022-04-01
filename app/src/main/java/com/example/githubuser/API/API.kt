package com.example.githubuser.API

import com.example.githubuser.Data.DataUserSearch
import com.example.githubuser.Data.UserDetail
import com.example.githubuser.Data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
//    Search
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUser(
        @Query("q") query: String
    ) : Call<UserResponse>
//   Detail
    @GET("users/{username}")
    @Headers("Authorization: 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")

    fun getUserDetail(
        @Path("username") username : String
    ) : Call<UserDetail>
//   list Follower
    @GET("users/{username}/followers")
    @Headers("Authorization: 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")

    fun getUserFollower(
        @Path("username") username: String
    ) : Call<ArrayList<DataUserSearch>>
//  list following
    @GET("users/{username}/following")
    @Headers("Authorization: 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")

    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<DataUserSearch>>

//

}