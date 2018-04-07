package ru.kszorin.seaworldkotlin.db

import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto

/**
 * Created on 04.04.2018.
 */
interface ISeaWorldDatabase {

    fun cleanDatabase()

    fun updateAnimal(animal: Animal)

    fun getStatistics(): StatisticsDto
}