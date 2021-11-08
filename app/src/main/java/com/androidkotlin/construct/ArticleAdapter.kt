package com.androidkotlin.construct


import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.armoireapp.R
import com.androidkotlin.models.Articles
import com.bumptech.glide.Glide

class ArticleAdapter(private  val articleList : ArrayList<Articles>) : RecyclerView.Adapter<ArticleAdapter.articleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_articles_layout,
        parent, false)
        return articleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: articleViewHolder, position: Int) {
        val currentitem = articleList[position]
        holder.name_article.text = currentitem.name_article
        holder.price_article.text = currentitem.price_article.toString()
        Glide.with(holder.image_article.context).load(currentitem.image_article).into(holder.image_article)

    }

    override fun getItemCount(): Int {
        return  articleList.size
    }

    class articleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name_article : TextView = itemView.findViewById(R.id.name_article_rw)
        val image_article : ImageView = itemView.findViewById(R.id.image_article_rw)
        val price_article : TextView = itemView.findViewById(R.id.price_article_rw)

    }
}
