package com.example.choplaygroundkotlin.team

class TeamItem {
    var name: String? = null
    var desc: String? = null
    var img = 0

    constructor(name: String?, desc: String?, img: Int) {
        this.name = name
        this.desc = desc
        this.img = img
    }

    constructor() {}

}