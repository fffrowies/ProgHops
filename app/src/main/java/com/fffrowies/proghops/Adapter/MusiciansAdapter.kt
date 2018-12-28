package com.fffrowies.proghops.Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fffrowies.proghops.Controller.MainActivity
import com.fffrowies.proghops.Model.Musician
import com.fffrowies.proghops.R
import com.fffrowies.proghops.Services.DataService.musician
import com.squareup.picasso.Picasso

class MusiciansAdapter(private val musicians: ArrayList<Musician>) : RecyclerView.Adapter<MusiciansAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: MusiciansAdapter.ViewHolder, position: Int) {
        Picasso.get().load(musicians[position].photoUrl).into(holder.image)
        holder.title.text = musicians[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusiciansAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.musician_row, parent, false)
        val holder = ViewHolder(view)

        view.setOnClickListener {
            // val intent = Intent(parent.context, MusicianDetails::class.java)
            val intent = Intent(parent.context, MainActivity::class.java)
            // intent.putExtra("name", musician[holder.adapterPosition].name )
            val mIndexPosition = holder.adapterPosition

            intent.putExtra("photoUrl", musician[holder.adapterPosition].photoUrl )
            intent.putExtra("name", musician[holder.adapterPosition].name )
            intent.putExtra("mIndexPosition", "$mIndexPosition" )

            parent.context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = musicians.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.photo)
        val title: TextView = itemView.findViewById(R.id.title)
    }
}