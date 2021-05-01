package com.example.cookbook.model

class Category {
    var catName: String? = null
    var imageUrl: String? = null
    var description: String? = null

    constructor() {}
    constructor(catName: String?, imageUrl: String?, description: String?) {
        this.catName = catName
        this.imageUrl = imageUrl
        this.description = description
    }
}