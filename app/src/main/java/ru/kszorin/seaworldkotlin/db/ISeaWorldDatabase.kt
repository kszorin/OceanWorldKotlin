package ru.kszorin.seaworldkotlin.db

import ru.kszorin.seaworldkotlin.entities.Animal

/**
 * Created on 04.04.2018.
 */
interface ISeaWorldDatabase {

    fun cleanDatabase()

    fun updateAnimal(animal: Animal)
}