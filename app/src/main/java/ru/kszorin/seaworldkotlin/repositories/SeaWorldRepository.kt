package ru.kszorin.seaworldkotlin.repositories

import ru.kszorin.seaworldkotlin.db.SeaWorldDatabase
import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.Creature
import ru.kszorin.seaworldkotlin.entities.Orca
import ru.kszorin.seaworldkotlin.entities.World
import ru.kszorin.seaworldkotlin.use_cases.ISeaWorldRepository
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
class SeaWorldRepository : ISeaWorldRepository {
    /**
     * The enter point in Entity layer.
     */
    val world = World()

    /**
     * Flag for interrupt caused by reset.
     */
    var nextStepFlag = false

    /**
     * The enter point in Database layer.
     */
    private var seaWorldDatabase: ISeaWorldDatabase = SeaWorldDatabase()

    override fun getFieldParameters(): InitDataDto {
        return InitDataDto(Pair(World.FIELD_SIZE_X, World.FIELD_SIZE_Y))
    }

    override fun cleanDatabase() {
        seaWorldDatabase.cleanDatabase()
    }

    override fun resetGame() {
        nextStepFlag = false
        world.reset()
    }

    override fun getNextStepObservable(delay: Long): Observable<CurrentStateDto> {
        return Observable.create({ subscriber ->
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

            //if creature is not animal => DEFAULT_AGE
            var age = DEFAULT_AGE

            //necessary for color indication
            var isStarvingDeathSoon = false
            var isChildbirthSoon = false

            //add alive animal in list only
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

    override fun getStatistics(): StatisticsDto {
        return seaWorldDatabase.getStatistics()
    }

    companion object {
        private val TAG = "SeaWorldRepository"
        private val DEFAULT_AGE = -1
    }
}