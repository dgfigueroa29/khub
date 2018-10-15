package com.boa.khub.viewmodel

import com.boa.khub.repository.ItemRepository
import com.boa.khub.repository.data.RepositoryResults
import com.boa.khub.viewmodel.data.RepositoriesList
import io.reactivex.Observable
import timber.log.Timber

class RepositoryListViewModel(private val itemRepository: ItemRepository){
	fun getRepositories(filter: String): Observable<RepositoriesList>{
		return itemRepository.getRepositories(filter)?.map{
			if(it is RepositoryResults){
				Timber.d("Mapping repositories to UIData...")
				RepositoriesList(it.items, "Top 50 Repositories")
			}else{
				Timber.d("Mapping repositories to UIData...")
				RepositoriesList(emptyList(), "Top 50 Repositories")
			}
		}?.onErrorReturn{
			Timber.d("Ha ocurrido un error $it")
			RepositoriesList(emptyList(), "Ha ocurrido un error", it)
		}!!
	}
}