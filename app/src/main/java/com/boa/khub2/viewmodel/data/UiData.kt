package com.boa.khub2.viewmodel.data

import com.boa.khub2.repository.data.Repository

data class RepositoriesList(
    val repositories: List<Repository>,
    val message: String,
    val error: Throwable? = null
)