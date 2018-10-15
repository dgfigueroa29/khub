package com.boa.khub

import android.app.Application
import android.arch.persistence.room.Room
import com.boa.khub.repository.ItemRepository
import com.boa.khub.repository.api.GithubApi
import com.boa.khub.repository.db.AppDatabase
import com.boa.khub.viewmodel.RepositoryListViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class App : Application(){
	companion object {
		private lateinit var retrofit: Retrofit
		private lateinit var githubApi: GithubApi
		private lateinit var itemRepository: ItemRepository
		private lateinit var repositoryListViewModel: RepositoryListViewModel
		private lateinit var appDatabase: AppDatabase
		
		fun injectGitHubApi() = githubApi
		fun injectRepositoryListViewModel() = repositoryListViewModel
		fun injectRepositoryDao() = appDatabase.repositoryDao()
	}
	
	override fun onCreate(){
		super.onCreate()
		Timber.uprootAll()
		Timber.plant(Timber.DebugTree())
		
		retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl("https://api.github.com/search/")
			.build()
		
		githubApi = retrofit.create(GithubApi::class.java)
		appDatabase = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java, "mvvm-database"
		).build()
		
		itemRepository = ItemRepository(githubApi, appDatabase.repositoryDao())
		repositoryListViewModel = RepositoryListViewModel(itemRepository)
	}
}