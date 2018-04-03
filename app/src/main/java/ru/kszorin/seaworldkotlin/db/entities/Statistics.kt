package ru.kszorin.seaworldkotlin.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created on 03.04.2018.
 */
@Entity
class Statistics (@PrimaryKey var id: Int,
                  var stepsAmount: Int
)