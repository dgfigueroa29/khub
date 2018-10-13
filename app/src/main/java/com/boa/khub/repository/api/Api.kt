package com.boa.khub.repository.api

import com.boa.khub.repository.data.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi{
	@GET("6de6abfedb24f889e0b5f675edc50deb?fmt=raw&sole")
	@Headers("Authorization: ea1dd74b9d2d964b7eab55ab59af5b8a1c46facc")
	fun getUsers(): Observable<List<User>>
}