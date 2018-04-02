package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
class SeaWorldInteractor(val seaWorldRepository: ISeaWorldRepository): ISeaWorldInteractor {


    override fun fieldInitialization(): InitDataDto {
        resetGame()
        return seaWorldRepository.getFieldData()
    }

    override fun getNextDataObservable(delay: Long): Observable<CurrentStateDto> {
        return seaWorldRepository.getNextStepObservable(delay)
    }

    /*    override fun doNextStep(delay: Long) {
        seaWorldRepository.nextStep(delay)
    }*/

    override fun getCurrentPosition(): CurrentStateDto {
        return seaWorldRepository.getCurrentState()
    }

    override fun resetGame() {
        seaWorldRepository.resetGame()
    }
}