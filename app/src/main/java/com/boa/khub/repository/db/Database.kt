package com.boa.khub.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.boa.khub.repository.data.Repository

@Database(entities = arrayOf(Repository::class), version = 2)
abstract class AppDatabase : RoomDatabase(){
	abstract fun repositoryDao(): RepositoryDao
}