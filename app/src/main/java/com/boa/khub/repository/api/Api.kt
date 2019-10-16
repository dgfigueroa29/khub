package com.boa.khub.repository.api

import com.boa.khub.repository.data.RepositoryResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi {
    @GET("repositories?sort=stars&order=desc")
    @Headers("Authorization: c155e18957eaf6cbb135704ecab1f03edfe0a75d")
    fun getRepositories(@Query("q") query: String): Observable<RepositoryResults>
}