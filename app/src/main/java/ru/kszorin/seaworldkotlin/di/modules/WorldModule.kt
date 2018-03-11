package ru.kszorin.seaworldkotlin.di.modules

import dagger.Module
import dagger.Provides
import ru.kszorin.seaworldkotlin.array2dOfInt
import ru.kszorin.seaworldkotlin.models.Creature
import java.util.*
import javax.inject.Singleton

/**
 * Created on 04.03.2018.
 */
@Module
class WorldModule(fieldSizeX: Int, fieldSizeY: Int) {

    private val waterSpace = array2dOfInt(fieldSizeX, fieldSizeY)
    private var creaturesMap: MutableMap<Int, Creature> = TreeMap()

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
}