package com.androidkotlin.construct

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.armoireapp.R
import com.androidkotlin.armoireapp.RecipeBreakfastReciclerViewActivity
import com.androidkotlin.armoireapp.RecipeLunchReciclerViewActivity
import com.androidkotlin.models.Recipes
import com.bumptech.glide.Glide

private val TAG = "RecyclerViewAdapter"


private var mImageNames: ArrayList<String> = ArrayList()
private var mImages: ArrayList<String> = ArrayList()
private var mDescription: ArrayList<String> = ArrayList()
private var mRecipe: ArrayList<ArrayList<String>> = ArrayList()
private var mContext: Context? = null

class RecipeAdapter_Breakfast(private  val recipeLunchList : ArrayList<Recipes>) : RecyclerView.Adapter<RecipeAdapter_Breakfast.recipeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_recipe_layout,
            parent, false)
        return recipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: recipeViewHolder, position: Int) {
        val currentitem = recipeLunchList[position]
        holder.name_recipe.text = currentitem.name_recipe
        Glide.with(holder.image_recipe.context).load(currentitem.image_recipe).into(holder.image_recipe)
        holder.button_recipe.setOnClickListener{
            val intent = Intent(mContext, RecipeBreakfastReciclerViewActivity::class.java)
            intent.putExtra("image_url", mImages?.get(position))
            intent.putExtra("image_name", mImageNames?.get(position))
            intent.putExtra("text_description", mDescription?.get(position))
            intent.putExtra("list", mRecipe?.get(position))
            mContext?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  recipeLunchList.size
    }

    class recipeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name_recipe : TextView = itemView.findViewById(R.id.name_recipe_lunch_rw)
        val image_recipe : ImageView = itemView.findViewById(R.id.image_recipe_lunch_rw)
        val button_recipe : CardView = itemView.findViewById(R.id.cardview_recipe_lunch_rw)

    }
}

fun RecyclerViewAdapterBF(
    context: Context,
    imageNames: ArrayList<String>,
    images: ArrayList<String>,
    description : ArrayList<String>,
    recipe: ArrayList<ArrayList<String>>) {
        mImageNames = imageNames
        mImages = images
        mDescription = description
        mRecipe = recipe
        mContext = context
        return
}
