package com.boa.khub.viewmodel.data

import com.boa.khub.repository.data.Repository

data class RepositoriesList(val repositories: List<Repository>, val message: String, val error: Throwable? = null)