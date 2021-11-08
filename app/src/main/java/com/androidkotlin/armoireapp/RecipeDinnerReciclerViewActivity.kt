package com.androidkotlin.armoireapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.bumptech.glide.Glide

class RecipeDinnerReciclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_breakfast_recicler_view)
        getIncomingIntent()
    }
    private fun getIncomingIntent() {
        if (intent.hasExtra("image_url") &&
            intent.hasExtra("image_name") &&
            intent.hasExtra("text_description") &&
            intent.hasExtra("list")) {
            val imageUrl = intent.getStringExtra("image_url")
            val imageName = intent.getStringExtra("image_name")
            val imageDescription = intent.getStringExtra("text_description")
            val imageList = intent.getStringArrayListExtra("list")
            setImage(imageUrl, imageName,imageDescription,imageList)

        }
    }


    private fun setImage(imageUrl: String?, imageName: String?, imageDescription: String?, imageList: ArrayList<String>? ) {
        val name = findViewById<TextView>(R.id.name_reciclerview_recipe_breakfast)
        name.text = imageName
        val image: ImageView = findViewById(R.id.image_reciclerview_recipe_breakfast)
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(image)
        val description = findViewById<TextView>(R.id.description_reciclerview_recipe_breakfast)
        description.setText(imageDescription)
        val adapter = ArrayAdapter(this, R.layout.list_item, imageList!!.toArray())
        val list = findViewById<ListView>(R.id.list_ingredient_reciclerview_recipe_breakfast)
        list.setAdapter(adapter)
    }
}