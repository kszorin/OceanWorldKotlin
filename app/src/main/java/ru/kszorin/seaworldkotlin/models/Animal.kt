package ru.kszorin.seaworldkotlin.models

import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.models.behaviour.IEatingBehaviour
import ru.kszorin.seaworldkotlin.models.behaviour.IMovingBehaviour
import ru.kszorin.seaworldkotlin.models.behaviour.IReproductionBehaviour
import javax.inject.Inject

/**
 * Created on 23.02.2018.
 */
abstract class Animal(id: Int, pos: Pair<Int, Int>) : Creature(id, pos) {

    init {
        SeaWorldApp.Companion.modelsComponent.inject(this)
    }

    @Inject
    lateinit var waterSpace: Array<IntArray>

    var age = 0
    var timeFromEating = 0

    abstract val eatingBehaviour: IEatingBehaviour
    abstract val movingBehaviour: IMovingBehaviour
    abstract val reproductionBehaviour: IReproductionBehaviour

    abstract fun createBaby()

    fun findPlacesForMoving(): List<Pair<Int, Int>> {
        TODO()
    }

    fun findPlacesForHunting(): List<Pair<Int, Int>> {
        TODO()
    }

}