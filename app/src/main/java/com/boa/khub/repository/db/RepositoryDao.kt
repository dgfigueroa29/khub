package com.boa.khub.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boa.khub.repository.data.Repository
import io.reactivex.Single

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repositories ORDER BY stars DESC")
    fun getRepositories(): Single<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: Repository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repositories: List<Repository>)
}