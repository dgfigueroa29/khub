package com.boa.khub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.boa.khub.App
import com.boa.khub.R
import com.boa.khub.viewmodel.data.RepositoriesList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.repositories_fragment.*
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class RepositoriesListFragment : MvvmFragment(){
	val repositoryListViewModel = App.injectRepositoryListViewModel()
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.repositories_fragment, container, false)
	}
	
	override fun onStart(){
		super.onStart()
		subscribe(
			repositoryListViewModel.getRepositories()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					Timber.d("Received UIModel $it repositories.")
					showRepositories(it)
				}, {
					Timber.w(it)
					showError()
				})
		)
	}
	
	fun showRepositories(data: RepositoriesList){
		if(data.error == null){
			repositoriesList.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, data.repositories)
		}else{
			if(data.error is ConnectException || data.error is UnknownHostException){
				Timber.d("Sin conexión, tal vez informe al repositorio que los datos cargados desde DB.")
			}else{
				showError()
			}
		}
	}
	
	fun showError(){
		Toast.makeText(context, "Ha ocurrido un error :(", Toast.LENGTH_SHORT).show()
	}
}