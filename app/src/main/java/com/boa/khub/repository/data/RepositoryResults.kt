package com.boa.khub.repository.data

data class RepositoryResults(
    val total_count: Long,
    val incomplete_results: Boolean,
    val items: List<Repository>
)