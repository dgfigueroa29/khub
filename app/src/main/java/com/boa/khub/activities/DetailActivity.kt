package com.boa.khub.activities

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.boa.khub.R
import com.boa.khub.repository.data.Repository
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity : AppCompatActivity(){
	companion object {
		@JvmStatic fun getIntent(context: Context, repository: Repository): Intent{
			val intent = Intent(context, DetailActivity::class.java)
			intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
			intent.putExtra("name", repository.name)
			intent.putExtra("stars", repository.stargazers_count.toString())
			intent.putExtra("forks", repository.forks_count.toString())
			intent.putExtra("description", repository.description)
			return intent
		}
	}
	
	override fun onCreate(savedInstanceState: Bundle?){
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		setSupportActionBar(toolbar)
		val name = intent.getStringExtra("name")
		supportActionBar?.title = name
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		val tvStarts: TextView
		val tvForks: TextView
		val tvName: TextView
		val tvDescription: TextView
		tvStarts = findViewById(R.id.tvStars)
		tvForks = findViewById(R.id.tvForks)
		tvName = findViewById(R.id.tvName)
		tvDescription = findViewById(R.id.tvDescription)
		tvStarts.setText(intent.getStringExtra("stars"))
		tvForks.setText(intent.getStringExtra("forks"))
		tvName.setText(intent.getStringExtra("name"))
		tvDescription.setText(intent.getStringExtra("description"))
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> {
				onBackPressed()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}