package com.fffrowies.proghops.Controller

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.fffrowies.proghops.Adapter.MusiciansAdapter
import com.fffrowies.proghops.R
import com.fffrowies.proghops.Services.DataService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val musicians = DataService.musician

        var spanCount = 2
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
        }
        val screenSize = resources.configuration.screenWidthDp
        if (screenSize > 720) {
            spanCount += 2
        }

        recycler_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, spanCount)
            adapter = MusiciansAdapter(musicians)
        }

        val photoUrl = intent.getStringExtra("photoUrl")
        val name = intent.getStringExtra("name")
        val indexPosition = intent.getStringExtra("indexPosition")

        if (photoUrl != null) {
            Picasso.get().load(photoUrl).into(imageMusicianLeft)
        }
        if (name != null) {
            textMusicianLeft.text = name
        }
        if (indexPosition != null) {
            musicians.removeAt(indexPosition.toInt())
            recycler_view.adapter?.notifyItemRemoved(indexPosition.toInt())
        }
    }
}
