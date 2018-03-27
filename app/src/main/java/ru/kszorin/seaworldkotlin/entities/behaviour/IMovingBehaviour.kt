package ru.kszorin.seaworldkotlin.entities.behaviour

import ru.kszorin.seaworldkotlin.entities.Animal

/**
 * Created on 23.02.2018.
 */
interface IMovingBehaviour {
    /**
     * Move animal from current position to one of the founded positions list.
     * @return true if move is success, false - otherwise
     */
    fun move(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean
}