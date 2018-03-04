package ru.kszorin.seaworldkotlin.models

import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.array2dOfInt
import java.util.*
import javax.inject.Inject

/**
 * Created on 24.02.2018.
 */
class World {

    init {
        SeaWorldApp.Companion.modelsComponent.inject(this)
    }

    private var creaturesNumber: MutableMap<Creature.Companion.Species, Int> = HashMap()
    private var creaturesIdCounter = 0

    @Inject
    lateinit var waterSpace: Array<IntArray>

    private var creaturesMap: MutableMap<Int, Creature> = TreeMap()

    fun reset() {
        creaturesNumber.put(Creature.Companion.Species.ORCA, FIELD_SIZE_X * FIELD_SIZE_Y * ORCAS_PERCENT_FILLING / 100)
        creaturesNumber.put(Creature.Companion.Species.PENGUIN, FIELD_SIZE_X * FIELD_SIZE_Y * PENGUINS_PERCENT_FILLING / 100)
        for (i in waterSpace.indices) {
            for (j in waterSpace[i].indices) {
                waterSpace[i][j] = FREE_WATER_CODE
            }
        }
        creaturesIdCounter = 0
        creaturesMap.clear()

        // create creatures and put them on the field
        for (species in creaturesNumber.keys) {
            for (i in 0 until creaturesNumber[species]!!) {
                creaturesMap.put(creaturesIdCounter, createCreatures(species, creaturesIdCounter, findFreePosition()))
                creaturesIdCounter++;
            }
        }
    }

    fun nextStep() {
        for (creature in creaturesMap.values.sortedWith(kotlin.Comparator({ t1, t2 -> t1.compareTo(t2) }))) {
            if (creature.isExists) {
                creature.lifeStep()
            }
        }
    }

    private fun findFreePosition(): Pair<Int, Int> {
        var randomPos: Int
        var posCandidate: Pair<Int, Int>

        do {
            randomPos = (Math.random() * FIELD_SIZE_Y * FIELD_SIZE_X).toInt()
            posCandidate = Pair(randomPos % FIELD_SIZE_X, randomPos / FIELD_SIZE_X)
        } while (waterSpace[posCandidate.second][posCandidate.first] != FREE_WATER_CODE)

        return posCandidate
    }

    companion object {
        private val TAG = "World"
        val FIELD_SIZE_X = 10
        val FIELD_SIZE_Y = 15
        private val ORCAS_PERCENT_FILLING = 5
        private val PENGUINS_PERCENT_FILLING = 100 - ORCAS_PERCENT_FILLING
        private val FREE_WATER_CODE = -1
    }
}