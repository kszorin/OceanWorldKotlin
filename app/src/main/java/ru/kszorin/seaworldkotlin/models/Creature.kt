package ru.kszorin.seaworldkotlin.models

import android.graphics.Bitmap
import ru.kszorin.seaworldkotlin.R

/**
 * Created on 23.02.2018.
 */
abstract class Creature(id : Int, pos : Pair<Int, Int>) {

    abstract val species : Species
    abstract val environs : Byte


    companion object {
        enum class Species(bmpId : Int) {ORCA(R.drawable.orca), PENGUIN(R.drawable.tux)}
    }

    abstract fun lifeStep()
}