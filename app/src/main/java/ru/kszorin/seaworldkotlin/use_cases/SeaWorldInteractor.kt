package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentPositionDto
import ru.kszorin.seaworldkotlin.use_cases.dto.FieldDataDto

/**
 * Created on 28.03.2018.
 */
class SeaWorldInteractor(val seaWorldRepository: ISeaWorldRepository): ISeaWorldInteractor {


    override fun fieldInitialization(): FieldDataDto {
        return seaWorldRepository.getFieldData()
    }

    override fun doNextStep(): CurrentPositionDto {
        seaWorldRepository.nextStep()
        return seaWorldRepository.getCurrentPosition()
    }
}