package ru.kszorin.seaworldkotlin.models.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.models.Animal
import ru.kszorin.seaworldkotlin.models.Creature
import ru.kszorin.seaworldkotlin.models.World
import javax.inject.Inject

/**
 * Created on 23.02.2018.
 */
class EnvironsMoving: IMovingBehaviour {
    val TAG = "EnvironsMoving"

    override fun move(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        val pos = animal.pos

        if (foundPositionsInEnvirons.isNotEmpty()) {
            // select random position
            val bufferRandomNum = (Math.random() * foundPositionsInEnvirons.size).toInt()
            val selectedFreePos = foundPositionsInEnvirons[bufferRandomNum]

            //move animal to new position
            animal.waterSpace[selectedFreePos.first][selectedFreePos.second] = animal.waterSpace[pos.first][pos.second]
            animal.waterSpace[pos.first][pos.second] = World.FREE_WATER_CODE
            animal.creaturesMap[animal.id]?.pos = selectedFreePos
            Log.d(TAG, "${animal.creaturesMap[animal.id]?.species?.name} (${animal.id}):" +
                    " [${pos.first}, ${pos.second}] -> [${selectedFreePos.first}, ${selectedFreePos.second}]")
            return true
        }
        return false
    }
}