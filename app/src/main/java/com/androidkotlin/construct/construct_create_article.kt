package com.androidkotlin.construct

class construct_create_article(
    image_article: String,
    name_article: String,
    mark_article: String,
    type_article: String,
    price_article: Double,
    description_article: String,
    volume_article: String?,
    weight_article: String?,
    unit_volume_article: String?,
    unit_weight_article: String?
){
    var image_article: String? = null
    var name_article: String? = null
    var mark_article: String? = null
    var type_article: String? = null
    var price_article: Double? =0.0
    var description_article: String? = null
    var volume_article:String? = null
    var weight_article: String? = null
    var unit_volume_article: String? = null
    var unit_weight_article: String? = null
    init {
        this.image_article = image_article
        this.name_article = name_article
        this.mark_article = mark_article
        this.type_article = type_article
        this.price_article = price_article
        this.description_article = description_article
        this.volume_article = volume_article
        this.weight_article = weight_article
        this.unit_volume_article = unit_volume_article
        this.unit_weight_article = unit_weight_article
    }
}
