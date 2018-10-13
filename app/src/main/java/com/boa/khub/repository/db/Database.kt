package com.boa.khub.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.boa.khub.repository.data.User


@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun userDao(): UserDao
}