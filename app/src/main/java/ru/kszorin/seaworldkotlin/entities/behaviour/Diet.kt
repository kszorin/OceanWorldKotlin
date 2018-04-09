package ru.kszorin.seaworldkotlin.entities.behaviour

import ru.kszorin.seaworldkotlin.entities.Animal

/**
 * Created on 23.02.2018.
 *
 * Implements diet eating behaviour.
 */
class Diet: IEatingBehaviour {
    override fun eat(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        return false
    }
}