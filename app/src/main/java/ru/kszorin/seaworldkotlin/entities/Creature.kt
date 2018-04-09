package ru.kszorin.seaworldkotlin.entities

import ru.kszorin.seaworldkotlin.R

/**
 * Created on 23.02.2018.
 *
 * Defines creature in sea world.
 * @param id creature id
 * @param pos creature position (x, y)
 */
abstract class Creature(val id: Int, var pos: Pair<Int, Int>) : Comparable<Creature> {

    abstract val species: Species

    /**
    * Radius for moving, hunting, reproduction.
    */
    abstract val environs: Byte

    /**
    * Actions on each life step.
    */
    abstract fun lifeStep()

    override fun compareTo(other: Creature): Int {
        // need for bypass from pos [0, 0] to [FIELD_SIZE_X-1, FIELD_SIZE_Y-1]
        return if (this.pos.second != other.pos.second) {
            this.pos.second - other.pos.second
        } else {
            this.pos.first - other.pos.first
        }
    }

    companion object {
        /**
         * Class defines species of creatures.
         * @param pngId png resource id
         * @param targets contains species on which hunting can be made
         */
        enum class Species(val pngId: Int, val targets: Set<Species>) {
            PENGUIN(R.drawable.tux, emptySet()),
            ORCA(R.drawable.orca, setOf(Creature.Companion.Species.PENGUIN));
        }
    }


}