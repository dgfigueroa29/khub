package com.boa.khub.viewmodel.data

import com.boa.khub.repository.data.User

data class UsersList(val users: List<User>, val message: String, val error: Throwable? = null)