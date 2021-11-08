package com.androidkotlin.armoireapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.construct.ArticleAdapter
import com.androidkotlin.models.Articles
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ArticlesFragment : Fragment(){
private lateinit var dbReference: DatabaseReference
private lateinit var articleReciclerView : RecyclerView
private lateinit var articleArrayList: ArrayList<Articles>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_articles, container, false)
        val create = v.findViewById<FloatingActionButton>(R.id.create_articles)
        articleReciclerView = v.findViewById(R.id.recicler_view_articles)
        articleReciclerView.layoutManager = LinearLayoutManager(this.context)
        articleReciclerView.setHasFixedSize(true)
        articleReciclerView.layoutManager = GridLayoutManager(this.context,2)
        articleArrayList = arrayListOf<Articles>()
        getArticleData()
        create.setOnClickListener{
            val intent = Intent(activity, CreateArticlesActivity::class.java)
            activity!!.startActivity(intent)
        }
        return v
    }

    private fun getArticleData() {
       dbReference = FirebaseDatabase.getInstance().getReference("Articles")
        dbReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val article = userSnapshot.getValue(Articles::class.java)
                        articleArrayList.add(article!!)
                    }
                    articleReciclerView.adapter = ArticleAdapter(articleArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}