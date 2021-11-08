package com.androidkotlin.armoireapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.construct.TaskAdapter
import com.androidkotlin.models.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class CalendarFragment : Fragment() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var taskReciclerView : RecyclerView
    private lateinit var taskArrayList: ArrayList<Task>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=  inflater.inflate(R.layout.fragment_calendar, container, false)
        val create = v.findViewById<FloatingActionButton>(R.id.create_task)
        taskReciclerView = v.findViewById(R.id.recicler_view_recipes_task)
        taskReciclerView.layoutManager = LinearLayoutManager(this.context)
        taskReciclerView.setHasFixedSize(true)
        taskArrayList = arrayListOf<Task>()
        getTaskData()
        create.setOnClickListener{
            val intent = Intent(activity, CreateTaskActivity::class.java)
            activity!!.startActivity(intent)
        }
        return v
    }
    private fun getTaskData() {
        dbReference = FirebaseDatabase.getInstance().getReference("Task")
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val task = userSnapshot.getValue(Task::class.java)
                        taskArrayList.add(task!!)
                    }
                    taskReciclerView.adapter = TaskAdapter(taskArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}




