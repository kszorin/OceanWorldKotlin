package ru.kszorin.seaworldkotlin.entities

import ru.kszorin.seaworldkotlin.SeaWorldApp
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created on 24.02.2018.
 */
class World {

    private var creaturesNumber: MutableMap<Creature.Companion.Species, Int> = HashMap()

    @Inject
    lateinit var waterSpace: Array<IntArray>

    @Inject
    lateinit var creaturesMap: MutableMap<Int, Creature>

    @set:[Inject Named("creaturesIdCounter")]
    var creaturesIdCounter: Int = 0

    init {
        SeaWorldApp.modelsComponent?.inject(this)
    }

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
                creaturesMap.put(creaturesIdCounter, createCreatures(species, creaturesIdCounter, occupyFreePosition(creaturesIdCounter)))
                creaturesIdCounter++;
            }
        }
    }

    fun nextStep() {
        for (creature in creaturesMap.values.sortedWith(kotlin.Comparator({ t1, t2 -> t1.compareTo(t2) }))) {
            creature.lifeStep()
        }
    }

    private fun occupyFreePosition(id: Int): Pair<Int, Int> {
        var randomPos: Int
        var posCandidate: Pair<Int, Int>

        do {
            randomPos = (Math.random() * FIELD_SIZE_Y * FIELD_SIZE_X).toInt()
            posCandidate = Pair(randomPos % FIELD_SIZE_X, randomPos / FIELD_SIZE_X)
        } while (waterSpace[posCandidate.second][posCandidate.first] != FREE_WATER_CODE)
        waterSpace[posCandidate.second][posCandidate.first] = id

        return posCandidate
    }

    companion object {
        private val TAG = "World"
        val FIELD_SIZE_X = 10
        val FIELD_SIZE_Y = 15
        private val ORCAS_PERCENT_FILLING = 5
        private val PENGUINS_PERCENT_FILLING = 50
        val FREE_WATER_CODE = -1
    }
}