package com.boa.khub.activities

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.boa.khub.R
import com.boa.khub.repository.data.Repository
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun getIntent(context: Context, repository: Repository): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("name", repository.name)
            intent.putExtra("stars", repository.stargazers_count.toString())
            intent.putExtra("forks", repository.forks_count.toString())
            intent.putExtra("description", repository.description)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        val name = intent.getStringExtra("name")
        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val tvStarts: TextView = findViewById(R.id.tvStars)
        val tvForks: TextView = findViewById(R.id.tvForks)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvDescription: TextView = findViewById(R.id.tvDescription)
        tvStarts.text = intent.getStringExtra("stars")
        tvForks.text = intent.getStringExtra("forks")
        tvName.text = intent.getStringExtra("name")
        tvDescription.text = intent.getStringExtra("description")
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