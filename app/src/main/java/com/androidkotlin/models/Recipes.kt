package com.androidkotlin.models

data class Recipes(
    var image_recipe: String? = null,
    var name_recipe: String? = null,
    var type_recipe: String? = null,
    var instructions_recipe: String? = null,
    var articles_recipe: ArrayList<*>? = null
)
