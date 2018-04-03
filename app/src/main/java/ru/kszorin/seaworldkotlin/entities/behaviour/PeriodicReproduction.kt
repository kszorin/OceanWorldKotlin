package ru.kszorin.seaworldkotlin.entities.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.CreaturesIdCounter
import javax.inject.Inject

/**
 * Created on 23.02.2018.
 */
class PeriodicReproduction: IReproductionBehaviour {
    val TAG = "PeriodicReproduction"

    @Inject
    lateinit var creaturesIdCounter: CreaturesIdCounter

    init {
        SeaWorldApp.modelsComponent?.inject(this)
    }

    override fun reproduce(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        val pos = animal.pos

        if (foundPositionsInEnvirons.isNotEmpty()) {
            // select random position
            val bufferRandomNum = (Math.random() * foundPositionsInEnvirons.size).toInt()
            val selectedFreePos = foundPositionsInEnvirons[bufferRandomNum]

            //create baby at the new position
            val baby = animal.createBaby(creaturesIdCounter.counter, selectedFreePos)
            animal.creaturesMap.put(creaturesIdCounter.counter, baby)
            animal.waterSpace[selectedFreePos.second][selectedFreePos.first] = creaturesIdCounter.counter

            Log.d(TAG, "${animal.creaturesMap[animal.id]?.species?.name} (${animal.id})" +
                    " [${pos.first}, ${pos.second}]: produced ${animal.creaturesMap[creaturesIdCounter.counter]?.species?.name}" +
                    "(${baby.id}) [${baby.pos.first}, ${baby.pos.second}]")

            for (cr in animal.creaturesMap) {
                Log.d(TAG, "${cr.key} - ${cr.value.species.name} [${cr.value.pos.first}, ${cr.value.pos.second}]")
            }
            for (i in animal.waterSpace.indices) {
                for (j in animal.waterSpace[i].indices) {
                    System.out.printf("%02d ", animal.waterSpace[i][j])
                }
                println()
            }

            creaturesIdCounter.counter++
            return true
        }
        return false
    }
}