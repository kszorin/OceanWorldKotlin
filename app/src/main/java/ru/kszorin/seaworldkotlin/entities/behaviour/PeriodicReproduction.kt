package ru.kszorin.seaworldkotlin.entities.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.BuildConfig
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.CreaturesIdCounter
import ru.kszorin.seaworldkotlin.entities.World
import javax.inject.Inject

/**
 * Created on 23.02.2018.
 */
class PeriodicReproduction: IReproductionBehaviour {

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

            if (BuildConfig.DEBUG_LOG) {
                World.logging(TAG, animal.creaturesMap, animal.waterSpace)
            }

            creaturesIdCounter.counter++
            return true
        }
        return false
    }

    companion object {
        val TAG = "PeriodicReproduction"
    }
}