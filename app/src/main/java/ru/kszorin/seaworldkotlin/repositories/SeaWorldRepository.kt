package ru.kszorin.seaworldkotlin.repositories

import android.util.Log
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

    override fun getFieldData(): InitDataDto {
        return InitDataDto(World.FIELD_SIZE_X, World.FIELD_SIZE_Y)
    }

    /*override fun nextStep(delay: Long) {
        world.nextStep(delay)
    }*/

    override fun getCurrentState(): CurrentStateDto {
        val creaturesList = mutableListOf<CreatureStepData>()
        for (creature in world.creaturesMap.values) {
            // TODO: add constant
            var age = -1
            if (creature.species.equals(Creature.Companion.Species.PENGUIN) || creature.species.equals(Creature.Companion.Species.ORCA)) {
                creature as Animal
                age = creature.age
            }

            var isStarvingDeathSoon = false
            creature as Animal
            val isChildbirthSoon = creature.age % creature.reproductionPeriod >= creature.reproductionPeriod - 1

            //orca has additional parameters for displaying
            if (creature.species.equals(Orca)) {
                creature as Orca

                isStarvingDeathSoon = creature.timeFromEating >= Orca.STARVING_DEATH_PERIOD - 1
            }

            creaturesList.add(CreatureStepData(
                    creature.species,
                    creature.pos,
                    age,
                    isStarvingDeathSoon,
                    isChildbirthSoon
            ))
        }
        return CurrentStateDto(creaturesList)
    }

    override fun getNextStepObservable(delay: Long): Observable<CurrentStateDto> {
        return Observable.create(Observable.OnSubscribe<CurrentStateDto> { subscriber ->
            for (creature in world.creaturesMap.values.sortedWith(kotlin.Comparator({ t1, t2 -> t1.compareTo(t2) }))) {
                if (delay > 0) {
                    Thread.sleep(delay)
                }
                creature.lifeStep()
                Log.d(TAG, "step was completed on thread ${Thread.currentThread()}")
                subscriber.onNext(getCurrentState())
            }
            subscriber.onCompleted()
        })
    }

    override fun resetGame() {
        world.reset()
    }

    companion object {
        val TAG = "SeaWorldRepository"
    }
}