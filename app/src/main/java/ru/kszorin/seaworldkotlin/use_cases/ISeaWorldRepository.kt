package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentPositionDto
import ru.kszorin.seaworldkotlin.use_cases.dto.FieldDataDto

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldRepository {

    fun nextStep()

    fun getCurrentPosition(): CurrentPositionDto

    fun getFieldData(): FieldDataDto

}