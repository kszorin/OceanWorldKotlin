package ru.kszorin.seaworldkotlin.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created on 03.04.2018.
 */
@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = Species::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("speciesId"))))
data class Animal(
        @PrimaryKey
        var id: Int,
        var isAlive: Boolean,
        var age: Int,
        var childrenNumber: Int,
        var eatenNumber: Int,
        var speciesId: Int
)