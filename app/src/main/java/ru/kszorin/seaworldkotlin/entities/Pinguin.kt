package ru.kszorin.seaworldkotlin.entities

import ru.kszorin.seaworldkotlin.entities.behaviour.Diet
import ru.kszorin.seaworldkotlin.entities.behaviour.EnvironsMoving
import ru.kszorin.seaworldkotlin.entities.behaviour.PeriodicReproduction

/**
 * Created on 23.02.2018.
 */
class Pinguin(id : Int, pos : Pair<Int, Int>) : Animal(id, pos) {

    override val species = Species.PENGUIN
    override val environs : Byte = ENVIRONS

    override val eatingBehaviour = Diet()
    override val movingBehaviour = EnvironsMoving()
    override val reproductionBehaviour = PeriodicReproduction()

    init {
        reproductionPeriod = REPRODUCTION_PERIOD
    }

    override fun lifeStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createBaby(id: Int, pos: Pair<Int, Int>): Animal {
        return Pinguin(id, pos)
    }

    companion object {
        private val REPRODUCTION_PERIOD: Byte = 3
        private val ENVIRONS: Byte = 1
    }
}