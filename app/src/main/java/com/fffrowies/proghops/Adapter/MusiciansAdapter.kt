package com.fffrowies.proghops.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fffrowies.proghops.Model.Musician
import com.fffrowies.proghops.R
import com.squareup.picasso.Picasso

class MusiciansAdapter(private val musicians: ArrayList<Musician>) : RecyclerView.Adapter<MusiciansAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: MusiciansAdapter.ViewHolder, position: Int) {
        Picasso.get().load(musicians[position].photoUrl).into(holder.image)
        holder.title.text = musicians[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusiciansAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.musician_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = musicians.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.photo)
        val title: TextView = itemView.findViewById(R.id.title)
    }
}