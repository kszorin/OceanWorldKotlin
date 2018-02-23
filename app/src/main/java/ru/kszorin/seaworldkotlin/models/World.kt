package ru.kszorin.seaworldkotlin.models

/**
 * Created on 24.02.2018.
 */
class World {
    companion object {
        private val FIELD_SIZE_X = 10
        private val FIELD_SIZE_Y = 15
        private val ORCAS_PERCENT_FILLING = 5
        private val PENGUINS_PERCENT_FILLING = 100 - ORCAS_PERCENT_FILLING
        private val CLEAR_WATER_CODE = -1
    }

    private var creaturesNumber: Map<Creature.Companion.Species, Int> = HashMap()
    private var creaturesIdCounter = 0
    private var waterSpace = array2dOfInt(FIELD_SIZE_Y, FIELD_SIZE_X)

}

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
        = Array(sizeOuter) { IntArray(sizeInner) }