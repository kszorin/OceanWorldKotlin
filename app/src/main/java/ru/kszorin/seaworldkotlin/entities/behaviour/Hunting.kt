package ru.kszorin.seaworldkotlin.entities.behaviour

import android.util.Log
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.World

/**
 * Created on 23.02.2018.
 */
class Hunting: IEatingBehaviour {
    val TAG = "Hunting"

    override fun eat(animal: Animal, foundPositionsInEnvirons: List<Pair<Int, Int>>): Boolean {
        val pos = animal.pos

        if (foundPositionsInEnvirons.isNotEmpty()) {
            // select random position
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

            for (cr in animal.creaturesMap) {
                Log.d("Hunting", "${cr.key} - ${cr.value.species.name} [${cr.value.pos.first}, ${cr.value.pos.second}]")
            }
            for (i in animal.waterSpace.indices) {
                for (j in animal.waterSpace[i].indices) {
                    System.out.printf("%02d ", animal.waterSpace[i][j])
                }
                println()
            }
            //TODO: decrease penguins numbers
            return true
        }
        return false
    }
}