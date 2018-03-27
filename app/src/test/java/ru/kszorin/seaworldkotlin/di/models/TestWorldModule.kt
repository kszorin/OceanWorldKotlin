package ru.kszorin.seaworldkotlin.di.models

import dagger.Module
import dagger.Provides
import ru.kszorin.seaworldkotlin.array2dOfInt
import ru.kszorin.seaworldkotlin.entities.Creature
import ru.kszorin.seaworldkotlin.entities.Orca
import ru.kszorin.seaworldkotlin.entities.Pinguin
import ru.kszorin.seaworldkotlin.entities.World
import java.util.*
import javax.inject.Singleton

/**
 * Created on 11.03.2018.
 */
@Module
class TestWorldModule {
    private val waterSpace = array2dOfInt(3, 3)
    private var creaturesMap: MutableMap<Int, Creature> = TreeMap()
    private var creaturesIdCounter = 4

    init {
        waterSpace[0] = intArrayOf(World.FREE_WATER_CODE, World.FREE_WATER_CODE, World.FREE_WATER_CODE)
        waterSpace[1] = intArrayOf(World.FREE_WATER_CODE,          1,           World.FREE_WATER_CODE)
        waterSpace[2] = intArrayOf(            2,         World.FREE_WATER_CODE,        3)

        creaturesMap.put(1, Pinguin(1, Pair(1, 1)))
        creaturesMap.put(2, Pinguin(2, Pair(0, 2)))
        creaturesMap.put(3, Orca(3, Pair(2, 2)))
    }

    @Provides
    @Singleton
    fun provideWaterSpace(): Array<IntArray> {
        return waterSpace
    }

    @Provides
    @Singleton
    fun provideCreaturesMap(): MutableMap<Int, Creature> {
        return creaturesMap
    }

    @Provides
    @Singleton
    fun provideCreaturesIdCounter(): Int {
        return creaturesIdCounter
    }

}