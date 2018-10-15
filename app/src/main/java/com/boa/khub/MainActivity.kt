package com.boa.khub

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boa.khub.view.RepositoriesListFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(){
	override fun onCreate(savedInstanceState: Bundle?){
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		if(savedInstanceState == null){
			supportFragmentManager.beginTransaction().replace(R.id.frag_container, RepositoriesListFragment()).commit()
		}
		
		setSupportActionBar(toolbar)
		setUpSearchView()
	}
	
	fun setUpSearchView(){
		/*val searchEditText = mainSearchCardView.getEditText()
		searchEditText.setText("kotlin")
		searchEditText.setSelection(searchEditText.getText().length);
		searchEditText.setHint(R.string.search_repositories)
		RxTextView.textChanges(searchEditText)
			.doOnNext { mainResultsSpinner.show() }
			.sample(1, TimeUnit.SECONDS)
			.switchMap { gitHubService.searchRepositories(it.toString()).subscribeOnIo() }
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
}