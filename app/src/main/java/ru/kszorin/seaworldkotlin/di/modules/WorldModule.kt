package ru.kszorin.seaworldkotlin.di.modules

import dagger.Module
import dagger.Provides
import ru.kszorin.seaworldkotlin.array2dOfInt
import ru.kszorin.seaworldkotlin.entities.Creature
import java.util.*
import javax.inject.Singleton

/**
 * Created on 04.03.2018.
 */
@Module
class WorldModule(fieldSizeX: Int, fieldSizeY: Int) {

    private val waterSpace = array2dOfInt(fieldSizeY, fieldSizeX)
    private val creaturesMap: MutableMap<Int, Creature> = TreeMap()
    private var creaturesIdCounter: Int = 0

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