package com.boa.khub.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boa.khub.R
import com.boa.khub.view.RepositoriesListFragment
import kotlinx.android.synthetic.main.toolbar.*

class DetailActivity : AppCompatActivity(){
	override fun onCreate(savedInstanceState: Bundle?){
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		setSupportActionBar(toolbar)
		
		if(savedInstanceState == null){
			supportFragmentManager.beginTransaction().replace(R.id.frag_container, RepositoriesListFragment()).commit()
		}
	}
}