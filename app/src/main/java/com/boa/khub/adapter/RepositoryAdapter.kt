package com.boa.khub.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boa.khub.R
import com.boa.khub.activities.DetailActivity
import com.boa.khub.repository.data.Repository
import java.util.*

class RepositoryAdapter(val context: Context) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    var repositories: List<Repository> = ArrayList()
    override fun getItemCount() = repositories.size

    fun loadRepositories(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    class RepositoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val repositoryItemTitle: TextView
        val repositoryItemStarCount: TextView
        val repositoryItemRootLayout: RelativeLayout

        init {
            v.setOnClickListener { Log.d("ADAPTER", "Element $adapterPosition clicked.") }
            repositoryItemTitle = v.findViewById(R.id.repositoryItemTitle)
            repositoryItemStarCount = v.findViewById(R.id.repositoryItemStarCount)
            repositoryItemRootLayout = v.findViewById(R.id.repositoryItemRootLayout)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RepositoryViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.repository_item, viewGroup, false)

        return RepositoryViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: RepositoryViewHolder, position: Int) {
        Log.d("ADAPTER", "Element $position set.")
        viewHolder.repositoryItemTitle.text = repositories.get(position).full_name
        viewHolder.repositoryItemStarCount.text =
            repositories.get(position).stargazers_count.toString()
        viewHolder.repositoryItemRootLayout.setOnClickListener {
            context.startActivity(
                DetailActivity.getIntent(
                    context,
                    repositories.get(position)
                )
            )
        }
    }
}