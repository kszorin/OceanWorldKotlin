package ru.kszorin.seaworldkotlin.entities.behaviour

import ru.kszorin.seaworldkotlin.entities.Animal

/**
 * Created on 23.02.2018.
 */
interface IReproductionBehaviour {
    fun reproduce (animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean
}