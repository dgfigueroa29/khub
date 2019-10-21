package com.boa.khub2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boa.khub2.R
import com.boa.khub2.view.RepositoriesListFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, RepositoriesListFragment()).commit()
        }
    }
}