package ru.kszorin.seaworldkotlin.models.behaviour

import ru.kszorin.seaworldkotlin.models.Animal

/**
 * Created on 23.02.2018.
 */
class Diet: IEatingBehaviour {
    override fun eat(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        return false
    }
}