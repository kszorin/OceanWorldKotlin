package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldRepository {

    fun getFieldData(): InitDataDto

    fun getNextStepObservable(delay: Long): Observable<CurrentStateDto>

    fun cleanDatabase()

    fun getCurrentState(): CurrentStateDto

    fun resetGame()

    fun getStatisticsObservable(): Observable<StatisticsDto>

}