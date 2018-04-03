package ru.kszorin.seaworldkotlin.entities

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import java.util.*
import javax.inject.Inject

/**
 * Created on 24.02.2018.
 */
class World {

    private var creaturesNumber: MutableMap<Creature.Companion.Species, Int> = HashMap()

    @Inject
    lateinit var waterSpace: Array<IntArray>

    @Inject
    lateinit var creaturesMap: MutableMap<Int, Creature>

    @Inject
    lateinit var creaturesIdCounter: CreaturesIdCounter

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
        creaturesIdCounter.counter = 0
        creaturesMap.clear()

        // create creatures and put them on the field
        for (species in creaturesNumber.keys) {
            for (i in 0 until creaturesNumber[species]!!) {
                creaturesMap.put(
                        creaturesIdCounter.counter,
                        createCreatures(species, creaturesIdCounter.counter, occupyFreePosition(creaturesIdCounter.counter))
                )
                creaturesIdCounter.counter++
            }
        }
    }

    /*    fun nextStep(delay: Long) {
            for (creature in creaturesMap.values.sortedWith(kotlin.Comparator({ t1, t2 -> t1.compareTo(t2) }))) {
                if (delay > 0) {
                    Thread.sleep(delay)
                }
                Log.d(TAG, "step was completed on thread ${Thread.currentThread()}")
                creature?.lifeStep()
            }
        }*/

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

        fun logging(tag: String, creaturesMap: MutableMap<Int, Creature>, waterSpace: Array<IntArray>) {
            for (cr in creaturesMap) {
                Log.d("Hunting", "${cr.key} - ${cr.value.species.name} [${cr.value.pos.first}, ${cr.value.pos.second}]")
            }
            for (i in waterSpace.indices) {
                for (j in waterSpace[i].indices) {
                    System.out.printf("%02d ", waterSpace[i][j])
                }
                println()
            }
        }
    }
}