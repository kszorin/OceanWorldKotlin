package ru.kszorin.seaworldkotlin.models

import android.graphics.Bitmap
import ru.kszorin.seaworldkotlin.R

/**
 * Created on 23.02.2018.
 */
abstract class Creature(val id: Int, var pos: Pair<Int, Int>) : Comparable<Creature> {

    abstract val species: Species
    abstract val environs: Byte
    var isAlive = true

    abstract fun lifeStep()

    override fun compareTo(other: Creature): Int {
        return if (this.pos.second != other.pos.second) {
            this.pos.second - other.pos.second
        } else {
            this.pos.first - other.pos.first
        }
    }

    companion object {
        enum class Species(val bmpId: Int) {
            ORCA(R.drawable.orca),
            PENGUIN(R.drawable.tux);
        }
    }


}