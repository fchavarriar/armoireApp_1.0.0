package com.androidkotlin.construct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.armoireapp.R
import com.androidkotlin.models.Task

class TaskAdapter(private  val taskList : ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.taskViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_task_layout,
            parent, false)
        return taskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: taskViewHolder, position: Int) {
        val currentitem = taskList[position]
        holder.type_task.text = currentitem.type_task
        holder.name_task.text = currentitem.name_task
        holder.date_task.text = currentitem.date_task
    }

    override fun getItemCount(): Int {
        return  taskList.size
    }

    class taskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val type_task : TextView = itemView.findViewById(R.id.type_recipe_task_rw)
        val name_task : TextView = itemView.findViewById(R.id.name_recipe_task_rw)
        val date_task : TextView = itemView.findViewById(R.id.date_recipe_task_rw)
    }
}