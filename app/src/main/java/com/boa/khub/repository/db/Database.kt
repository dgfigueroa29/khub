package com.boa.khub.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boa.khub.repository.data.Repository

@Database(entities = [Repository::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}