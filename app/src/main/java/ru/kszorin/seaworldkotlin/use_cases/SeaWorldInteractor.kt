package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto

/**
 * Created on 28.03.2018.
 */
class SeaWorldInteractor(val seaWorldRepository: ISeaWorldRepository): ISeaWorldInteractor {


    override fun fieldInitialization(): InitDataDto {
        resetGame()
        return seaWorldRepository.getFieldData()
    }

    override fun doNextStep() {
        seaWorldRepository.nextStep()
    }

    override fun getCurrentPosition(): CurrentStateDto {
        return seaWorldRepository.getCurrentState()
    }

    override fun resetGame() {
        seaWorldRepository.resetGame()
    }
}