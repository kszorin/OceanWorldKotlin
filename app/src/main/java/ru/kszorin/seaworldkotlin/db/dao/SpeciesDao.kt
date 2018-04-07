package ru.kszorin.seaworldkotlin.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.kszorin.seaworldkotlin.db.entities.Species

/**
 * Created on 03.04.2018.
 */
@Dao
interface SpeciesDao {

    @Insert
    fun insert(species: Species)

    @Query("SELECT * FROM species")
    fun getAll(): List<Species>

    @Query("DELETE FROM species")
    fun deleteAll()
}