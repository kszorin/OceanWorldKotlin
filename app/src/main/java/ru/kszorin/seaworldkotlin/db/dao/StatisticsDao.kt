package ru.kszorin.seaworldkotlin.db.dao

import android.arch.persistence.room.*
import ru.kszorin.seaworldkotlin.db.entities.Statistics

/**
 * Created on 03.04.2018.
 */

@Dao
interface StatisticsDao {

    @Insert
    fun insert(penguin: Statistics)

    @Query("SELECT * FROM orca")
    fun getAll(): List<Statistics>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(): List<Statistics>

    @Query("DELETE * FROM statistics")
    fun deleteAll()
}