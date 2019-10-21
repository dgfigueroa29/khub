package com.boa.khub2.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.boa.khub2.App
import com.boa.khub2.R
import com.boa.khub2.adapter.RepositoryAdapter
import com.boa.khub2.viewmodel.data.RepositoriesList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.repositories_fragment.*
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class RepositoriesListFragment : MvvmFragment() {
    private val repositoryListViewModel = App.injectRepositoryListViewModel()
    lateinit var repositoryAdapter: RepositoryAdapter
    var filter: String = "khub2"
    private val prefsName = "com.boa.khub2.prefs"
    var prefs: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repositories_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        prefs = activity!!.getSharedPreferences(prefsName, 0)
        filter = prefs!!.getString("filter", "khub2")!!
        setUpRecyclerView()
        setUpSearchView()
        reload()
    }

    private fun reload() {
        filter = prefs!!.getString("filter", "khub2")!!
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

    private fun setUpRecyclerView() {
        repositoryAdapter = RepositoryAdapter(activity?.applicationContext!!)
        repositoriesList.adapter = repositoryAdapter
        repositoriesList.layoutManager =
            LinearLayoutManager(activity?.applicationContext!!)
    }

    private fun showRepositories(data: RepositoriesList) {
        if (data.error == null) {
            repositoryAdapter = RepositoryAdapter(activity?.applicationContext!!)
            repositoryAdapter.loadRepositories(data.repositories)
            repositoriesList.adapter = repositoryAdapter
        } else {
            if (data.error is ConnectException || data.error is UnknownHostException) {
                Timber.d("Sin conexión, tal vez informe al repositorio que los datos cargados desde DB.")
            } else {
                showError()
            }
        }
    }

    private fun setUpSearchView() {
        val searchEditText = mainSearchCardView.getEditText()
        searchEditText.setText(filter)
        searchEditText.setSelection(searchEditText.getText().length);
        searchEditText.setHint(R.string.search_repositories)
        searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val editor = prefs!!.edit()
                editor.putString("filter", searchEditText.text.toString())
                editor.apply()
                reload()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun showError() {
        Toast.makeText(context, "Ha ocurrido un error :(", Toast.LENGTH_SHORT).show()
    }
}