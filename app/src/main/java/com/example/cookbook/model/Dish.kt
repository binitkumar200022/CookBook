package com.example.cookbook.model

class Dish {
    var instructions: String? = null
    var cookTime: String? = null
    var difficultyLevel: String? = null
    var ingredients: String? = null
    var youtubeUrl: String? = null
    var measures: String? = null
    var dishName: String? = null
    var imageUrl: String? = null
    var source: String? = null
    var uid: String? = null

    constructor() {}
    constructor(
        measures: String?,
        instructions: String?,
        cookTime: String?,
        difficultyLevel: String?,
        ingredients: String?,
        youtubeUrl: String?,
        dishName: String?,
        imageUrl: String?,
        source: String?,
        uid: String?
    ) {
        this.measures = measures
        this.instructions = instructions
        this.cookTime = cookTime
        this.difficultyLevel = difficultyLevel
        this.ingredients = ingredients
        this.youtubeUrl = youtubeUrl
        this.dishName = dishName
        this.imageUrl = imageUrl
        this.source = source
        this.uid = uid
    }
}