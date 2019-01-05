package com.fffrowies.proghops.Controller

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.fffrowies.proghops.HopsActivity
import com.fffrowies.proghops.Model.Musician
import com.fffrowies.proghops.R
import com.fffrowies.proghops.Services.DataService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    // holds how many clicks on images from grid
    var selected = 0

    // holds data to refill the array of musicians
    var textImageLeftActual = ""
    var photoUrlLeftActual = ""
    var textImageRightActual = ""
    var photoUrlRightActual = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val musicians = DataService.musician

        if (savedInstanceState != null) {
            selected = savedInstanceState.getInt("selected_key")
            textImageLeftActual = savedInstanceState.getString("txv_image_left_key")
            photoUrlLeftActual = savedInstanceState.getString("photo_url_left_key")
            textImageRightActual = savedInstanceState.getString("txv_image_right_key")
            photoUrlRightActual = savedInstanceState.getString("photo_url_right_key")

            textMusicianLeft.text = textImageLeftActual
            Picasso.get().load(photoUrlLeftActual).into(imageMusicianLeft)
            textMusicianRight.text = textImageRightActual
            Picasso.get().load(photoUrlRightActual).into(imageMusicianRight)
        }

        var spanCount = 2
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
            onRestoreInstanceState(savedInstanceState)
        }
        val screenSize = resources.configuration.screenWidthDp
        if (screenSize > 720) {
            spanCount += 2
        }

        recycler_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, spanCount)
            adapter = MusiciansAdapter(musicians)
        }

        spark_button.setOnClickListener {
            if (selected > 1) {
                startHopsCalc()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("selected_key", selected)
        outState.putString("txv_image_left_key", textImageLeftActual)
        outState.putString("photo_url_left_key", photoUrlLeftActual)
        outState.putString("txv_image_right_key", textImageRightActual)
        outState.putString("photo_url_right_key", photoUrlRightActual)
    }

    inner class MusiciansAdapter(private val musicians: ArrayList<Musician>) : RecyclerView.Adapter<MusiciansAdapter.ViewHolder>() {
        override fun onBindViewHolder(holder: MusiciansAdapter.ViewHolder, position: Int) {
            Picasso.get().load(musicians[position].photoUrl).into(holder.image)
            holder.title.text = musicians[position].name
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusiciansAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.musician_row, parent, false)
            val holder = ViewHolder(view)

            view.setOnClickListener {

                val indexPosition = holder.adapterPosition

                if (selected == 0) {
                    textInstructions.text = getText(R.string.pick_one)
                } else {
                    textInstructions.text = ""
                    spark_button.playAnimation()
                    spark_button.isChecked = true
                }

                distribute(selected, indexPosition)

                selected++

                musicians.removeAt(indexPosition)
                this.notifyItemRemoved(indexPosition)
            }
            return holder
        }

        override fun getItemCount() = musicians.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image: ImageView = itemView.findViewById(R.id.photo)
            val title: TextView = itemView.findViewById(R.id.title)
        }

        private fun distribute(selected: Int, indexPosition: Int) {
            if (selected % 2 == 0) {
                if (selected > 0) {
                    musicians.add(Musician(textImageLeftActual, photoUrlLeftActual))
                }
                textMusicianLeft.text = DataService.musician[indexPosition].name
                Picasso.get().load(DataService.musician[indexPosition].photoUrl).into(imageMusicianLeft)
                textImageLeftActual = DataService.musician[indexPosition].name
                photoUrlLeftActual = DataService.musician[indexPosition].photoUrl
            } else {
                if (selected > 1) {
                    musicians.add(Musician(textImageRightActual, photoUrlRightActual))
                }
                textMusicianRight.text = DataService.musician[indexPosition].name
                Picasso.get().load(DataService.musician[indexPosition].photoUrl).into(imageMusicianRight)
                textImageRightActual = DataService.musician[indexPosition].name
                photoUrlRightActual = DataService.musician[indexPosition].photoUrl
            }
        }
    }

    private fun startHopsCalc() {

        val intent = Intent(this, HopsActivity::class.java)

        startActivity(intent)
    }
}
