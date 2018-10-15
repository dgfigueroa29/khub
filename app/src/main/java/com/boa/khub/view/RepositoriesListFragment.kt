package com.boa.khub.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.boa.khub.App
import com.boa.khub.R
import com.boa.khub.adapter.RepositoryAdapter
import com.boa.khub.viewmodel.data.RepositoriesList
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.repositories_fragment.*
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class RepositoriesListFragment : MvvmFragment(){
	val repositoryListViewModel = App.injectRepositoryListViewModel()
	lateinit var repositoryAdapter: RepositoryAdapter
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.repositories_fragment, container, false)
	}
	
	override fun onStart(){
		super.onStart()
		setUpRecyclerView()
		setUpSearchView()
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
		searchEditText.setText("kotlin")
		searchEditText.setSelection(searchEditText.getText().length);
		searchEditText.setHint(R.string.search_repositories)
		/*RxTextView.textChanges(searchEditText)
			.doOnNext { mainResultsSpinner.show() }
			.sample(1, TimeUnit.SECONDS)
			.switchMap { App.injectGitHubApi().getRepositories(it.toString()).subscribeOnIo() }
			.subscribeUntilDestroy(this) {
				onNext {
					mainResultsSpinner.hide()
					repositoryAdapter.loadRepositories(it)
				}
				onError {
					Timber.e(it, "Failed to load repositories")
					mainResultsSpinner.hide()
					alert {
						setTitle(R.string.error)
						setMessage(R.string.search_repositories_error)
						setPositiveButton(android.R.string.ok, null)
					}
				}
			}*/
	}
	
	fun showError(){
		Toast.makeText(context, "Ha ocurrido un error :(", Toast.LENGTH_SHORT).show()
	}
}