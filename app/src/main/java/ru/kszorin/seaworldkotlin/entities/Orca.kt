package ru.kszorin.seaworldkotlin.entities

import ru.kszorin.seaworldkotlin.entities.behaviour.EnvironsMoving
import ru.kszorin.seaworldkotlin.entities.behaviour.Hunting
import ru.kszorin.seaworldkotlin.entities.behaviour.PeriodicReproduction

/**
 * Created on 23.02.2018.
 */
class Orca(id : Int, pos : Pair<Int, Int>) : Animal(id, pos) {

    override val species = Species.ORCA
    override val environs: Byte = ENVIRONS

    override val eatingBehaviour = Hunting()
    override val movingBehaviour = EnvironsMoving()
    override val reproductionBehaviour = PeriodicReproduction()

    init {
        reproductionPeriod = REPRODUCTION_PERIOD
    }

    override fun lifeStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createBaby(id: Int, pos: Pair<Int, Int>): Animal  {
        return Orca(id, pos)
    }

    companion object {
        val REPRODUCTION_PERIOD: Byte = 8
        private val ENVIRONS: Byte = 1
        val HUNGER_DEATH_PERIOD: Byte = 3
    }
}