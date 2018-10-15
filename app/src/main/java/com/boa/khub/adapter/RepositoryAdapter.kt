package com.boa.khub.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.boa.khub.R
import com.boa.khub.repository.data.Repository
import java.util.ArrayList

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>(){
	var repositories: List<Repository> = ArrayList()
	override fun getItemCount() = repositories.size
	
	fun loadRepositories(repositories: List<Repository>){
		this.repositories = repositories
		notifyDataSetChanged()
	}
	
	class RepositoryViewHolder(v: View) : RecyclerView.ViewHolder(v){
		val repositoryItemTitle: TextView
		val repositoryItemStarCount: TextView
		
		init{
			v.setOnClickListener { Log.d("ADAPTER", "Element $adapterPosition clicked.") }
			repositoryItemTitle = v.findViewById(R.id.repositoryItemTitle)
			repositoryItemStarCount = v.findViewById(R.id.repositoryItemStarCount)
		}
	}
	
	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RepositoryViewHolder{
		// Create a new view.
		val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.repository_item, viewGroup, false)
		
		return RepositoryViewHolder(v)
	}
	
	override fun onBindViewHolder(viewHolder: RepositoryViewHolder, position: Int){
		Log.d("ADAPTER", "Element $position set.")
		viewHolder.repositoryItemTitle.text = repositories.get(position).full_name
		viewHolder.repositoryItemStarCount.text = repositories.get(position).stargazers_count.toString()
	}
}