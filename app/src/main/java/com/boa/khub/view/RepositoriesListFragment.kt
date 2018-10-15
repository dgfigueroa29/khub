package com.boa.khub.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.boa.khub.App
import com.boa.khub.R
import com.boa.khub.adapter.RepositoryAdapter
import com.boa.khub.viewmodel.data.RepositoriesList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.repositories_fragment.*
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class RepositoriesListFragment : MvvmFragment(){
	val repositoryListViewModel = App.injectRepositoryListViewModel()
	lateinit var repositoryAdapter: RepositoryAdapter
	var filter:String = "khub"
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.repositories_fragment, container, false)
	}
	
	override fun onStart(){
		super.onStart()
		setUpRecyclerView()
		setUpSearchView()
		reload()
	}
	
	fun reload(){
		subscribe(
			repositoryListViewModel.getRepositories(filter)
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
	
	fun setUpRecyclerView(){
		repositoryAdapter = RepositoryAdapter(activity?.applicationContext!!)
		repositoriesList.adapter = repositoryAdapter
		repositoriesList.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
	}
	
	fun showRepositories(data: RepositoriesList){
		if(data.error == null){
			repositoryAdapter = RepositoryAdapter(activity?.applicationContext!!)
			repositoryAdapter.loadRepositories(data.repositories)
			repositoriesList.adapter = repositoryAdapter
		}else{
			if(data.error is ConnectException || data.error is UnknownHostException){
				Timber.d("Sin conexión, tal vez informe al repositorio que los datos cargados desde DB.")
			}else{
				showError()
			}
		}
	}
	
	fun setUpSearchView(){
		val searchEditText = mainSearchCardView.getEditText()
		searchEditText.setText(filter)
		searchEditText.setSelection(searchEditText.getText().length);
		searchEditText.setHint(R.string.search_repositories)
		searchEditText.setOnEditorActionListener { v, actionId, event ->
			if(actionId == EditorInfo.IME_ACTION_SEARCH){
				filter = searchEditText.text.toString()
				reload()
				return@setOnEditorActionListener true
			}
			
			return@setOnEditorActionListener false
		}
	}
	
	fun showError(){
		Toast.makeText(context, "Ha ocurrido un error :(", Toast.LENGTH_SHORT).show()
	}
}