package com.boa.khub.repository.api

import com.boa.khub.repository.data.RepositoryResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi{
	@GET("repositories")
	@Headers("Authorization: ea1dd74b9d2d964b7eab55ab59af5b8a1c46facc")
	fun getRepositories(@Query("q") query: String): Observable<RepositoryResults>
}