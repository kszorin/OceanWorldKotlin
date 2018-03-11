package ru.kszorin.seaworldkotlin.models.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.models.Animal
import ru.kszorin.seaworldkotlin.models.World

/**
 * Created on 23.02.2018.
 */
class Hunting: IEatingBehaviour {
    val TAG = "Hunting"
    override fun eat(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        val pos = animal.pos

        if (foundPositionsInEnvirons.isNotEmpty()) {
            // select random victim
            val bufferRandomNum = (Math.random() * foundPositionsInEnvirons.size).toInt()
            val selectedFreePos = foundPositionsInEnvirons[bufferRandomNum]

            //kill victim and move animal to new position

            val victimId = animal.waterSpace[selectedFreePos.second][selectedFreePos.first]
            Log.d(TAG, "${animal.creaturesMap[animal.id]?.species?.name} (${animal.id})" +
                    " [${pos.first}, ${pos.second}]: killed ${animal.creaturesMap[victimId]?.species?.name} (${victimId})" +
                    " [${selectedFreePos.first}, ${selectedFreePos.second}]")
            animal.creaturesMap.remove(victimId)

            animal.waterSpace[selectedFreePos.second][selectedFreePos.first] = animal.waterSpace[pos.second][pos.first]
            animal.waterSpace[pos.second][pos.first] = World.FREE_WATER_CODE
            animal.creaturesMap[animal.id]?.pos = selectedFreePos

            return true
        }
        return false
    }
}