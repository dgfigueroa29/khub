package com.boa.khub

import android.app.Application
import android.arch.persistence.room.Room
import com.boa.khub.repository.UserRepository
import com.boa.khub.repository.api.UserApi
import com.boa.khub.repository.db.AppDatabase
import com.boa.khub.viewmodel.UserListViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class App : Application(){
	//For the sake of simplicity, for now we use this instead of Dagger
	companion object {
		private lateinit var retrofit: Retrofit
		private lateinit var userApi: UserApi
		private lateinit var userRepository: UserRepository
		private lateinit var userListViewModel: UserListViewModel
		private lateinit var appDatabase: AppDatabase
		
		fun injectUserApi() = userApi
		fun injectUserListViewModel() = userListViewModel
		fun injectUserDao() = appDatabase.userDao()
	}
	
	override fun onCreate(){
		super.onCreate()
		Timber.uprootAll()
		Timber.plant(Timber.DebugTree())
		
		retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl("https://api.github.com/search/repositories?q=q")//TODO cambiar filtro
			.build()
		
		userApi = retrofit.create(UserApi::class.java)
		appDatabase = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java, "mvvm-database"
		).build()
		
		userRepository = UserRepository(userApi, appDatabase.userDao())
		userListViewModel = UserListViewModel(userRepository)
	}
}