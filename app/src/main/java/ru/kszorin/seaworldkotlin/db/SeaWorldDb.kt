package ru.kszorin.seaworldkotlin.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.kszorin.seaworldkotlin.db.dao.AnimalDao
import ru.kszorin.seaworldkotlin.db.dao.SpeciesDao
import ru.kszorin.seaworldkotlin.db.dao.StatisticsDao
import ru.kszorin.seaworldkotlin.db.entities.Animal
import ru.kszorin.seaworldkotlin.db.entities.Species
import ru.kszorin.seaworldkotlin.db.entities.Statistics

/**
 * Created on 03.04.2018.
 */
@Database(entities = arrayOf(Animal::class, Species::class, Statistics::class), version = 1)
abstract class SeaWorldDb: RoomDatabase()  {

    abstract fun getOrcaDao(): AnimalDao

    abstract fun getPenguinDao(): SpeciesDao

    abstract fun getStatisticsDao(): StatisticsDao
}