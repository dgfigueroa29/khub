package com.boa.khub.repository

import com.boa.khub.repository.api.GithubApi
import com.boa.khub.repository.data.Repository
import com.boa.khub.repository.data.RepositoryResults
import com.boa.khub.repository.db.RepositoryDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ItemRepository(val githubApi: GithubApi, val repositoryDao: RepositoryDao){
	fun getRepositories(filter: String): Observable<Any>? {
		return Observable.concatArray(
			getRepositoriesFromDb(),
			getRepositoriesFromApi(filter)
		)
	}
	
	fun getRepositoriesFromDb(): Observable<List<Repository>>{
		return repositoryDao.getRepositories().filter {it.isNotEmpty()}
			.toObservable()
			.doOnNext {
				Timber.d("Dispatching ${it.size} repositories from DB...")
			}
	}
	
	fun getRepositoriesFromApi(filter: String): Observable<RepositoryResults>? {
		return githubApi.getRepositories(filter)
			.doOnNext {
				Timber.d("Filtering with ${filter} ...")
				Timber.d("Dispatching ${it.items} repositories from API...")
				storeRepositoriesInDb(it.items)
			}
	}
	
	fun storeRepositoriesInDb(repositories: List<Repository>){
		Observable.fromCallable{repositoryDao.insertAll(repositories)}
			.subscribeOn(Schedulers.io())
			.observeOn(Schedulers.io())
			.subscribe {
				Timber.d("Inserted ${repositories.size} repositories from API in DB...")
			}
	}
}