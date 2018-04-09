package ru.kszorin.seaworldkotlin.entities

import android.util.Log
import ru.kszorin.seaworldkotlin.BuildConfig
import ru.kszorin.seaworldkotlin.entities.behaviour.EnvironsMoving
import ru.kszorin.seaworldkotlin.entities.behaviour.Hunting
import ru.kszorin.seaworldkotlin.entities.behaviour.PeriodicReproduction

/**
 * Created on 23.02.2018.
 */
class Orca(id: Int, pos: Pair<Int, Int>) : Animal(id, pos) {

    override val species = Species.ORCA
    override val environs: Byte = ENVIRONS

    override val eatingBehaviour = Hunting()
    override val movingBehaviour = EnvironsMoving()
    override val reproductionBehaviour = PeriodicReproduction()

    override val reproductionPeriod = REPRODUCTION_PERIOD

    override fun lifeStep() {
        //try hunting, if unsuccessful - try move
        if (eatingBehaviour.eat(this, findVictims())) {
            timeFromEating = 0
            if (BuildConfig.DEBUG_LOG) {
                Log.d(TAG, "id = $id, success hunting")
            }
        } else {
            timeFromEating++
            if (BuildConfig.DEBUG_LOG) {
                Log.d(TAG, "id = $id, unsuccessful hunting")
            }
            moving()
        }

        //check on starving death
        if (timeFromEating >= STARVING_DEATH_PERIOD) {
            waterSpace[pos.second][pos.first] = World.FREE_WATER_CODE
            this.isAlive = false
            if (BuildConfig.DEBUG_LOG) {
                Log.d(TAG, "${creaturesMap[id]?.species?.name} (${id}):" +
                        " [${pos.first}, ${pos.second}]: died of hungry!")
            }
        } else {
            //reproduction
            age++;
            reproduction()
        }
    }

    override fun createBaby(id: Int, pos: Pair<Int, Int>): Animal {
        return Orca(id, pos)
    }

    companion object {
        private val TAG = "Orca"
        val REPRODUCTION_PERIOD: Byte = 8
        private val ENVIRONS: Byte = 1
        val STARVING_DEATH_PERIOD: Byte = 3
    }
}