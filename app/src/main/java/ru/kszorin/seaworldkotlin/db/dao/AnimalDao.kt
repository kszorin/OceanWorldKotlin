package ru.kszorin.seaworldkotlin.db.dao

import android.arch.persistence.room.*
import ru.kszorin.seaworldkotlin.db.entities.Animal

/**
 * Created on 03.04.2018.
 */
@Dao
interface AnimalDao {

    @Insert
    fun insert(animal: Animal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(animal: Animal): Int

    @Query("SELECT * FROM animal")
    fun getAll(): List<Animal>

    @Query("DELETE FROM animal")
    fun deleteAll()
}