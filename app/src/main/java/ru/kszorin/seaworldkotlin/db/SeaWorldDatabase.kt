package ru.kszorin.seaworldkotlin.db

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.db.entities.Species
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.Creature
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsRecord
import javax.inject.Inject

/**
 * Created on 05.04.2018.
 */
class SeaWorldDatabase : ISeaWorldDatabase {

    @Inject
    lateinit var seaWorldRoomDatabase: SeaWorldRoomDatabase

    init {
        SeaWorldApp.dbComponent?.inject(this)
    }

    override fun cleanDatabase() {
        seaWorldRoomDatabase.getAnimalDao().deleteAll()
        seaWorldRoomDatabase.getSpeciesDao().deleteAll()

        seaWorldRoomDatabase.getSpeciesDao().insert(Species(Creature.Companion.Species.ORCA.ordinal, "Orca"))
        seaWorldRoomDatabase.getSpeciesDao().insert(Species(Creature.Companion.Species.PENGUIN.ordinal, "Penguin"))

        Log.d(TAG, "Database is empty")
    }

    override fun updateAnimal(animal: Animal) {
        val animalDb = ru.kszorin.seaworldkotlin.db.entities.Animal(
                animal.id,
                animal.isAlive,
                animal.age,
                animal.childrenNumber,
                animal.eatenNumber,
                animal.species.ordinal
        )

        if (seaWorldRoomDatabase.getAnimalDao().update(animalDb) == 0) {
            seaWorldRoomDatabase.getAnimalDao().insert(animalDb)
        }
    }

    override fun getStatistics(): StatisticsDto {
        val animalsList = seaWorldRoomDatabase.getAnimalDao().getAll()
        var statisticsRecordList = mutableListOf<StatisticsRecord>()

        for (animal in animalsList) {
            val species = when (animal.speciesId) {
                Creature.Companion.Species.ORCA.ordinal -> Creature.Companion.Species.ORCA.name
                else -> Creature.Companion.Species.PENGUIN.name
            }
            statisticsRecordList.add(StatisticsRecord(
                    animal.id,
                    species,
                    animal.age,
                    animal.isAlive,
                    animal.childrenNumber,
                    animal.eatenNumber
            ))
        }
        return StatisticsDto(statisticsRecordList)
    }

    companion object {
        val TAG = "SeaWorldDatabase"
    }
}