package com.boa.khub.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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

class RepositoriesListFragment : MvvmFragment() {
    val repositoryListViewModel = App.injectRepositoryListViewModel()
    lateinit var repositoryAdapter: RepositoryAdapter
    var filter: String = "khub"
    val prefs_name = "com.boa.khub.prefs"
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
        prefs = activity!!.getSharedPreferences(prefs_name, 0)
        filter = prefs!!.getString("filter", "khub")!!
        setUpRecyclerView()
        setUpSearchView()
        reload()
    }

    fun reload() {
        filter = prefs!!.getString("filter", "khub")!!
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

    fun setUpRecyclerView() {
        repositoryAdapter = RepositoryAdapter(activity?.applicationContext!!)
        repositoriesList.adapter = repositoryAdapter
        repositoriesList.layoutManager =
            LinearLayoutManager(activity?.applicationContext!!)
    }

    fun showRepositories(data: RepositoriesList) {
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

    fun setUpSearchView() {
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

    fun showError() {
        Toast.makeText(context, "Ha ocurrido un error :(", Toast.LENGTH_SHORT).show()
    }
}