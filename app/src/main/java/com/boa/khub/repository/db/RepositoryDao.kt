package com.boa.khub.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.boa.khub.repository.data.Repository
import io.reactivex.Single

@Dao
interface RepositoryDao{
	@Query("SELECT * FROM repositories")
	fun getRepositories(): Single<List<Repository>>
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(repository: Repository)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(repositories: List<Repository>)
}