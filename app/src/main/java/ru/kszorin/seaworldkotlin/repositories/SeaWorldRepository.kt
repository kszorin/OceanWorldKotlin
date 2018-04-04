package ru.kszorin.seaworldkotlin.repositories

import android.util.Log
import ru.kszorin.seaworldkotlin.SeaWorldApp
import ru.kszorin.seaworldkotlin.db.SeaWorldDb
import ru.kszorin.seaworldkotlin.db.entities.Species
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.Creature
import ru.kszorin.seaworldkotlin.entities.Orca
import ru.kszorin.seaworldkotlin.entities.World
import ru.kszorin.seaworldkotlin.use_cases.ISeaWorldRepository
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import rx.Observable
import javax.inject.Inject

/**
 * Created on 28.03.2018.
 */
class SeaWorldRepository : ISeaWorldRepository {
    val world = World()

    var nextStepFlag = false

    @Inject
    lateinit var seaWorldDb: SeaWorldDb

    init {
        SeaWorldApp.dbComponent?.inject(this)
    }

    override fun getFieldData(): InitDataDto {
        return InitDataDto(World.FIELD_SIZE_X, World.FIELD_SIZE_Y)
    }

    override fun cleanDatabase() {
        seaWorldDb.getAnimalDao().deleteAll()
        seaWorldDb.getSpeciesDao().deleteAll()

        seaWorldDb.getSpeciesDao().insert(Species(Creature.Companion.Species.ORCA.ordinal, "Orca"))
        seaWorldDb.getSpeciesDao().insert(Species(Creature.Companion.Species.PENGUIN.ordinal, "Penguin"))

        Log.d(TAG, "Database is empty")
    }

    override fun getNextStepObservable(delay: Long): Observable<CurrentStateDto> {
        return Observable.create(Observable.OnSubscribe<CurrentStateDto> { subscriber ->
            nextStepFlag = true
            try {

                for (creature in world.creaturesMap.values.sortedWith(kotlin.Comparator({ t1, t2 -> t1.compareTo(t2) }))) {
                    if (!nextStepFlag) {
                        break
                    }
                    if (delay > 0) {
                        Thread.sleep(delay)
                    }
                    if ((creature as Animal).isAlive) {
                        creature.lifeStep()
                        subscriber.onNext(getCurrentState())
                    }
                }
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
            subscriber.onCompleted()
        })
    }

    override fun getCurrentState(): CurrentStateDto {
        val creaturesList = mutableListOf<CreatureStepData>()
        for (creature in world.creaturesMap.values) {
            var age = DEFAULT_AGE
            var isStarvingDeathSoon = false
            var isChildbirthSoon = false
            var isAddInList = true

            if (creature.species.equals(Creature.Companion.Species.PENGUIN) || creature.species.equals(Creature.Companion.Species.ORCA)) {
                creature as Animal

                isAddInList = creature.isAlive

                age = creature.age
                isChildbirthSoon = creature.age % creature.reproductionPeriod >= creature.reproductionPeriod - 1

                //orca has additional parameters for displaying
                if (creature.species.equals(Orca)) {
                    creature as Orca

                    isStarvingDeathSoon = creature.timeFromEating >= Orca.STARVING_DEATH_PERIOD - 1
                }

                //insert data to DB
                val animal = ru.kszorin.seaworldkotlin.db.entities.Animal(
                        creature.id,
                        creature.isAlive,
                        creature.age,
                        creature.childrenNumber,
                        creature.eatenNumber,
                        creature.species.ordinal
                )

                if (seaWorldDb.getAnimalDao().update(animal) == 0) {
                    seaWorldDb.getAnimalDao().insert(animal)
                }
            }

            //add alive animal in list only
            if (isAddInList) {
                creaturesList.add(CreatureStepData(
                        creature.species,
                        creature.pos,
                        age,
                        isStarvingDeathSoon,
                        isChildbirthSoon
                ))
            }
        }
        return CurrentStateDto(creaturesList)
    }

    override fun resetGame() {
        nextStepFlag = false
        world.reset()
    }

    companion object {
        private val TAG = "SeaWorldRepository"
        private val DEFAULT_AGE = -1
    }
}