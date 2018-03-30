package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldRepository {

    fun getFieldData(): InitDataDto

    fun nextStep()

    fun getCurrentState(): CurrentStateDto

    fun resetGame()

}