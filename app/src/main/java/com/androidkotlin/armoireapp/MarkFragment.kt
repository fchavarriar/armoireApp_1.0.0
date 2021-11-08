package com.androidkotlin.armoireapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.construct.ArticleAdapter
import com.androidkotlin.construct.MarkAdapter
import com.androidkotlin.models.Articles
import com.androidkotlin.models.Marks
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class MarkFragment : Fragment() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var markReciclerView : RecyclerView
    private lateinit var markArrayList: ArrayList<Marks>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_mark, container, false)
        val create = v.findViewById<FloatingActionButton>(R.id.create_mark)
        markReciclerView = v.findViewById(R.id.recicler_view_articles)
        markReciclerView.layoutManager = LinearLayoutManager(this.context)
        markReciclerView.setHasFixedSize(true)
        markArrayList = arrayListOf<Marks>()
        getMarkData()
        create.setOnClickListener{
            val intent = Intent(activity, CreateMarkActivity::class.java)
            activity!!.startActivity(intent)
        }
        return v
    }
    private fun getMarkData() {
        dbReference = FirebaseDatabase.getInstance().getReference("Marks")
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val mark = userSnapshot.getValue(Marks::class.java)
                        markArrayList.add(mark!!)
                    }
                    markReciclerView.adapter = MarkAdapter(markArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}