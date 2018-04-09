package ru.kszorin.seaworldkotlin.use_cases.dto

import ru.kszorin.seaworldkotlin.entities.Creature

/**
 * Created on 27.03.2018.
 */
data class CreatureStepData(
        val species: Creature.Companion.Species,
        val pos: Pair<Int, Int>,
        val age:Int,
        val isStarvingDeathSoon: Boolean,
        val isChildbirthSoon: Boolean
)