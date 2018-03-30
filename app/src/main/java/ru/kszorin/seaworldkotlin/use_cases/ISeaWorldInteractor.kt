package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldInteractor {

    fun fieldInitialization(): InitDataDto

    fun getCurrentPosition(): CurrentStateDto

    fun doNextStep()

    fun resetGame()
}