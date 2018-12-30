package com.fffrowies.proghops.Controller

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
import com.fffrowies.proghops.Model.Musician
import com.fffrowies.proghops.R
import com.fffrowies.proghops.Services.DataService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    var selected = 0
    var textImageLeftActual = ""
    var photoUrlLeftActual = ""
    var textImageRightActual = ""
    var photoUrlRightActual = ""

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
                    textInstructions.text = "Pick One" // R.string.pick_one.toString()
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
                textMusicianLeft.text = DataService.musician[indexPosition].name
                Picasso.get().load(DataService.musician[indexPosition].photoUrl).into(imageMusicianLeft)
                textImageLeftActual = DataService.musician[indexPosition].name
                photoUrlLeftActual = DataService.musician[indexPosition].photoUrl
                if (selected > 0) {
                    musicians.add(Musician(textImageLeftActual, photoUrlLeftActual))
                }
            } else {
                textMusicianRight.text = DataService.musician[indexPosition].name
                Picasso.get().load(DataService.musician[indexPosition].photoUrl).into(imageMusicianRight)
                textImageRightActual = DataService.musician[indexPosition].name
                photoUrlRightActual = DataService.musician[indexPosition].photoUrl
                if (selected > 1) {
                    musicians.add(Musician(textImageRightActual, photoUrlRightActual))
                }
            }
        }
    }
}
