package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldRepository {

    fun getFieldData(): InitDataDto

    fun getNextStepObservable(delay: Long): Observable<CurrentStateDto>

    //fun nextStep(delay: Long)

    fun getCurrentState(): CurrentStateDto

    fun resetGame()

}