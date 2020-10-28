package com.lucasdonato.marcadordetruco.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class PlayersDao(name: String, score: Int = 0) : BaseObservable() {

    @get:Bindable
    var name: String = name
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    var score: Int = score
        set(value) {
            field = value
            notifyChange()
        }

}