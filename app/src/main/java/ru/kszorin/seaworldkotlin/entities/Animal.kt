package ru.kszorin.seaworldkotlin.entities

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.entities.behaviour.IEatingBehaviour
import ru.kszorin.seaworldkotlin.entities.behaviour.IMovingBehaviour
import ru.kszorin.seaworldkotlin.entities.behaviour.IReproductionBehaviour
import java.util.function.Function
import javax.inject.Inject

/**
 * Created on 23.02.2018.
 */
abstract class Animal(id: Int, pos: Pair<Int, Int>) : Creature(id, pos) {
    val TAG = "Animal"

    init {
        SeaWorldApp.modelsComponent?.inject(this)
    }

    @Inject
    lateinit var waterSpace: Array<IntArray>

    @Inject
    lateinit var creaturesMap: MutableMap<Int, Creature>

    var age = 0
    var timeFromEating = 0

    var reproductionPeriod = 0.toByte()

    abstract val eatingBehaviour: IEatingBehaviour
    abstract val movingBehaviour: IMovingBehaviour
    abstract val reproductionBehaviour: IReproductionBehaviour

    /**
     * Creates new animal.
     * @param pos position in which new animal will be created
     * @param id id of new animal
     */
    abstract fun createBaby(id: Int, pos: Pair<Int, Int>): Animal

    /**
     * @return list of suitable places for moving
     */
    fun findPlacesForMoving(): List<Pair<Int, Int>> {
        // condition: position is not occupied by anyone
        return findInEnvirons(Function { potentialPositionValue -> World.FREE_WATER_CODE == potentialPositionValue })
    }

    /**
     * @return list of suitable targets for hunting
     */
    fun findVictims(): List<Pair<Int, Int>> {
        // condition: position is occupied by animals-victims
        return findInEnvirons(Function { potentialTargetId ->
            potentialTargetId != World.FREE_WATER_CODE
                    && species.targets.contains(creaturesMap[potentialTargetId]?.species)
        })
    }

    private fun findInEnvirons(conditionForAddPosition: Function<Int, Boolean>): List<Pair<Int, Int>> {
        //define environs border by X
        var beginRangeBypassX = pos.first - environs
        if (beginRangeBypassX < 0) {
            beginRangeBypassX = 0
        }

        var endRangeBypassX = pos.first + environs
        if (endRangeBypassX > waterSpace[0].lastIndex) {
            endRangeBypassX = waterSpace[0].lastIndex
        }

        //define environs border by Y
        var beginRangeBypassY = pos.second - environs
        if (beginRangeBypassY < 0) {
            beginRangeBypassY = 0
        }

        var endRangeBypassY = pos.second + environs
        if (endRangeBypassY > waterSpace.lastIndex) {
            endRangeBypassY = waterSpace.lastIndex
        }

        //search suitable positions
        val foundPositions = mutableListOf<Pair<Int, Int>>()
        for (i in beginRangeBypassY..endRangeBypassY) {
            for (j in beginRangeBypassX..endRangeBypassX) {
                if (i == pos.second && j == pos.first) {
                    continue
                } else {
                    if (conditionForAddPosition.apply(waterSpace[i][j])) {
                        Log.d(TAG, "x = $j, y = $i, id in this position = ${waterSpace[i][j]}")
                        foundPositions.add(Pair(j, i))
                    }
                }
            }
        }

        return foundPositions
    }

}