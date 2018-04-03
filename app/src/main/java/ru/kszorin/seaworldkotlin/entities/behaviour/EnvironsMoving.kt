package ru.kszorin.seaworldkotlin.entities.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.BuildConfig
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.World

/**
 * Created on 23.02.2018.
 */
class EnvironsMoving: IMovingBehaviour {

    override fun move(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        val pos = animal.pos

        if (foundPositionsInEnvirons.isNotEmpty()) {
            // select random position
            val bufferRandomNum = (Math.random() * foundPositionsInEnvirons.size).toInt()
            val selectedFreePos = foundPositionsInEnvirons[bufferRandomNum]

            //move animal to new position
            animal.waterSpace[selectedFreePos.second][selectedFreePos.first] = animal.waterSpace[pos.second][pos.first]
            animal.waterSpace[pos.second][pos.first] = World.FREE_WATER_CODE
            animal.creaturesMap[animal.id]?.pos = selectedFreePos
            Log.d(TAG, "${animal.creaturesMap[animal.id]?.species?.name} (${animal.id}):" +
                    " [${pos.first}, ${pos.second}] -> [${selectedFreePos.first}, ${selectedFreePos.second}]")

            if (BuildConfig.DEBUG_LOG) {
                World.logging(TAG, animal.creaturesMap, animal.waterSpace)
            }

            return true
        }
        return false
    }

    companion object {
        private val TAG = "EnvironsMoving"
    }
}