package ru.kszorin.seaworldkotlin.entities.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.entities.Animal
import javax.inject.Inject
import javax.inject.Named

/**
 * Created on 23.02.2018.
 */
class PeriodicReproduction: IReproductionBehaviour {
    val TAG = "PeriodicReproduction"

    @set:[Inject Named("creaturesIdCounter")]
    var creaturesIdCounter: Int = 0

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
            val baby = animal.createBaby(creaturesIdCounter, selectedFreePos)
            animal.creaturesMap.put(creaturesIdCounter, baby)
            animal.waterSpace[pos.second][pos.first] = creaturesIdCounter

            Log.d(TAG, "${animal.creaturesMap[animal.id]?.species?.name} (${animal.id})" +
                    " [${pos.first}, ${pos.second}]: produced ${animal.creaturesMap[creaturesIdCounter]?.species?.name}" +
                    "($creaturesIdCounter) [${selectedFreePos.first}, ${selectedFreePos.second}]")

            creaturesIdCounter++
            return true
        }
        return false
    }
}