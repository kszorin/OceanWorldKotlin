package ru.kszorin.seaworldkotlin.entities

import android.util.Log
import ru.kszorin.seaworldkotlin.BuildConfig

/**
 * Created on 26.02.2018.
 */
fun createCreatures(species: Creature.Companion.Species, id: Int, pos: Pair<Int, Int>) : Creature {
    val TAG = "CreaturesFactory"

    val creature = when (species) {
        Creature.Companion.Species.ORCA -> Orca(id, pos)
        Creature.Companion.Species.PENGUIN -> Pinguin(id, pos)
    }
    if (BuildConfig.DEBUG_LOG) {
        Log.d(TAG, "${species.name} (id = $id) was created and put in [${pos.first}, ${pos.second}]")
    }

    return creature
}