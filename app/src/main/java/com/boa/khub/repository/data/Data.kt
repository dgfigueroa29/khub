package com.boa.khub.repository.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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