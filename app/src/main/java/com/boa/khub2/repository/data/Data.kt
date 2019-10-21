package com.boa.khub2.repository.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey
    val id: Long,
    val name: String,
    val full_name: String,
    val html_url: String,
    val description: String?,
    val url: String,
    @ColumnInfo(name = "stars")
    val stargazers_count: Long,
    val watchers_count: Long,
    val score: Double,
    @ColumnInfo(name = "forks")
    val forks_count: Long
)