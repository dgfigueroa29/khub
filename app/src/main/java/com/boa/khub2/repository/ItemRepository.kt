package com.boa.khub2.repository

import com.boa.khub2.repository.api.GithubApi
import com.boa.khub2.repository.data.Repository
import com.boa.khub2.repository.data.RepositoryResults
import com.boa.khub2.repository.db.RepositoryDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ItemRepository(private val githubApi: GithubApi, private val repositoryDao: RepositoryDao) {
    fun getRepositories(filter: String): Observable<Any>? {
        return Observable.concatArray(
            getRepositoriesFromDb(),
            getRepositoriesFromApi(filter)
        )
    }

    private fun getRepositoriesFromDb(): Observable<List<Repository>> {
        return repositoryDao.getRepositories().filter { it.isNotEmpty() }
            .toObservable()
            .doOnNext {
                Timber.d("Dispatching ${it.size} repositories from DB...")
            }
    }

    private fun getRepositoriesFromApi(filter: String): Observable<RepositoryResults>? {
        return githubApi.getRepositories(filter)
            .doOnNext {
                Timber.d("Filtering with $filter ...")
                Timber.d("Dispatching ${it.items} repositories from API...")
                storeRepositoriesInDb(it.items)
            }
    }

    fun storeRepositoriesInDb(repositories: List<Repository>) {
        Observable.fromCallable { repositoryDao.insertAll(repositories) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Timber.d("Inserted ${repositories.size} repositories from API in DB...")
            }
    }
}