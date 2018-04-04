package ru.kszorin.seaworldkotlin.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.kszorin.seaworldkotlin.db.dao.AnimalDao
import ru.kszorin.seaworldkotlin.db.dao.SpeciesDao
import ru.kszorin.seaworldkotlin.db.entities.Animal
import ru.kszorin.seaworldkotlin.db.entities.Species

/**
 * Created on 03.04.2018.
 */
@Database(entities = arrayOf(Animal::class, Species::class), version = 1)
abstract class SeaWorldDb: RoomDatabase()  {

    abstract fun getAnimalDao(): AnimalDao

    abstract fun getSpeciesDao(): SpeciesDao
}