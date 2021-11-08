package com.androidkotlin.armoireapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.construct.RecipeAdapter_Dinner
import com.androidkotlin.construct.RecipeAdapter_Lunch
import com.androidkotlin.construct.RecyclerViewAdapterBF
import com.androidkotlin.construct.RecyclerViewAdapterD
import com.androidkotlin.models.Recipes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DinnerFragment : Fragment() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: FirebaseDatabase
    private lateinit var recipeReciclerView : RecyclerView
    private lateinit var recipeArrayList: ArrayList<Recipes>
    private var mNames: ArrayList<String> = ArrayList()
    private val mImageUrls: ArrayList<String> = ArrayList()
    private var mDescription: ArrayList<String> = ArrayList()
    private val mRecipe: ArrayList<ArrayList<String>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_dinner, container, false)
        auth = FirebaseAuth.getInstance()
        dataBase= FirebaseDatabase.getInstance()
        recipeReciclerView = v.findViewById(R.id.recicler_view_recipes_dinner)
        recipeReciclerView.layoutManager = LinearLayoutManager(this.context)
        recipeReciclerView.setHasFixedSize(true)
        RecyclerViewAdapterD(this.context!!,mNames,mImageUrls,mDescription,mRecipe)
        recipeReciclerView.layoutManager = GridLayoutManager(this.context,1)
        recipeArrayList = arrayListOf<Recipes>()
        getRecipeDinnerData()
        return v
    }
    private fun getRecipeDinnerData() {
        dbReference = FirebaseDatabase.getInstance().getReference("Recipes_dinner")
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val recipe = userSnapshot.getValue(Recipes::class.java)
                        mNames.add(userSnapshot.child("name_recipe").value as String)
                        mImageUrls.add(userSnapshot.child("image_recipe").value as String)
                        mDescription.add(userSnapshot.child("instructions_recipe").value as String)
                        mRecipe.add(userSnapshot.child("articles_recipe").value as ArrayList<String>)
                        recipeArrayList.add(recipe!!)

                    }
                    recipeReciclerView.adapter = RecipeAdapter_Dinner(recipeArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}