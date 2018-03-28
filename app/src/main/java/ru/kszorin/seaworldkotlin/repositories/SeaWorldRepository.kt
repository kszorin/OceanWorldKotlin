package ru.kszorin.seaworldkotlin.repositories

import ru.kszorin.seaworldkotlin.entities.Animal
import ru.kszorin.seaworldkotlin.entities.Orca
import ru.kszorin.seaworldkotlin.entities.Pinguin
import ru.kszorin.seaworldkotlin.entities.World
import ru.kszorin.seaworldkotlin.use_cases.ISeaWorldRepository
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentPositionDto
import ru.kszorin.seaworldkotlin.use_cases.dto.FieldDataDto

/**
 * Created on 28.03.2018.
 */
class SeaWorldRepository : ISeaWorldRepository {
    val world = World()

    override fun getFieldData(): FieldDataDto {
        return FieldDataDto(World.FIELD_SIZE_X, World.FIELD_SIZE_Y)
    }

    override fun nextStep() {
        world.nextStep()
    }

    override fun getCurrentPosition(): CurrentPositionDto {
        val creaturesList = mutableListOf<CreatureStepData>()
        for (creature in world.creaturesMap.values) {
            // TODO: add constant
            var age = -1
            if (creature.species.equals(Pinguin) || creature.species.equals(Orca)) {
                creature as Animal
                age = creature.age
            }

            creaturesList.add(CreatureStepData(creature.species, creature.pos, age))
        }
        return CurrentPositionDto(creaturesList)
    }
}