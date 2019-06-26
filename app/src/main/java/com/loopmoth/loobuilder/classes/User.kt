package com.loopmoth.loobuilder.classes

class User {
    var user_id: String? = null
    var koszykTest: KoszykTest? = null

    constructor()
    constructor(user_id: String?){
        this.user_id = user_id
    }
}