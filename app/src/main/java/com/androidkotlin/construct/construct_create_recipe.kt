package com.androidkotlin.construct

class construct_create_recipe(
    image_recipe: String,
    name_recipe: String,
    type_recipe: String,
    instructions_recipe: String,
    articles_recipe: ArrayList<*>
){
    var image_recipe: String? = null
    var name_recipe: String? = null
    var type_recipe: String? = null
    var instructions_recipe: String? = null
    var articles_recipe: ArrayList<*>? = null
    init {
        this.image_recipe = image_recipe
        this.name_recipe = name_recipe
        this.type_recipe = type_recipe
        this.instructions_recipe = instructions_recipe
        this.articles_recipe = articles_recipe
    }
}
