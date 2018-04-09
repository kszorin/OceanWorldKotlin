package ru.kszorin.seaworldkotlin.repositories

import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto

/**
 * Created on 04.04.2018.
 */
interface ISeaWorldDatabase {

    /**
     * Method cleans game database.
     */
    fun cleanDatabase()

    /**
     * Method adds new or updates creature info in the database.
     */
    fun updateAnimal(animal: Animal)

    /**
     * Method for getting data from database.
     * @return DTO with statistics data from database.
     */
    fun getStatistics(): StatisticsDto
}