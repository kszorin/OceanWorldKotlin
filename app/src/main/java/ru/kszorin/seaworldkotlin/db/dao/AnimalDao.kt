package ru.kszorin.seaworldkotlin.db.dao

import android.arch.persistence.room.*
import ru.kszorin.seaworldkotlin.db.entities.Animal

/**
 * Created on 03.04.2018.
 */
@Dao
interface AnimalDao {

    @Insert
    fun insert(orca: Animal)

    @Query("SELECT * FROM orca")
    fun getAll(): List<Animal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(): List<Animal>

    @Query("DELETE * FROM orca")
    fun deleteAll()
}