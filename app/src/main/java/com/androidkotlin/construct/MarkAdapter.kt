package com.androidkotlin.construct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.armoireapp.R
import com.androidkotlin.models.Marks
class MarkAdapter(private  val markList : ArrayList<Marks>) : RecyclerView.Adapter<MarkAdapter.recipeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_mark_layout,
            parent, false)
        return recipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: recipeViewHolder, position: Int) {
        val currentitem = markList[position]
        holder.name_mark.text = currentitem.name_mark
    }

    override fun getItemCount(): Int {
        return  markList.size
    }

    class recipeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name_mark : TextView = itemView.findViewById(R.id.name_mark_rw)
    }
}