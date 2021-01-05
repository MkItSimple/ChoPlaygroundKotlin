package com.example.choplaygroundkotlin.portfolio

import java.io.Serializable

class PortfolioItem : Serializable {
    var image = 0
    var title: String? = null
    var description: String? = null

    constructor() {}
    constructor(image: Int) {
        this.image = image
    }

    constructor(image: Int, title: String?) {
        this.image = image
        this.title = title
    }

    constructor(image: Int, title: String?, description: String?) {
        this.image = image
        this.title = title
        this.description = description
    }

}