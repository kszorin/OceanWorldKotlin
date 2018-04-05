package ru.kszorin.seaworldkotlin.repositories

import ru.kszorin.seaworldkotlin.db.ISeaWorldDatabase
import ru.kszorin.seaworldkotlin.db.SeaWorldDatabase
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.Creature
import ru.kszorin.seaworldkotlin.entities.Orca
import ru.kszorin.seaworldkotlin.entities.World
import ru.kszorin.seaworldkotlin.use_cases.ISeaWorldRepository
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
class SeaWorldRepository : ISeaWorldRepository {
    val world = World()

    var nextStepFlag = false

    private var seaWorldDatabase: ISeaWorldDatabase = SeaWorldDatabase()

    override fun getFieldData(): InitDataDto {
        return InitDataDto(World.FIELD_SIZE_X, World.FIELD_SIZE_Y)
    }

    override fun cleanDatabase() {
        seaWorldDatabase.cleanDatabase()
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
                seaWorldDatabase.updateAnimal(creature)
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