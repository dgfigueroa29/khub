package com.boa.khub

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boa.khub.view.RepositoriesListFragment

class MainActivity : AppCompatActivity(){
	override fun onCreate(savedInstanceState: Bundle?){
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		if(savedInstanceState == null){
			supportFragmentManager.beginTransaction().replace(R.id.frag_container, RepositoriesListFragment()).commit()
		}
	}
}