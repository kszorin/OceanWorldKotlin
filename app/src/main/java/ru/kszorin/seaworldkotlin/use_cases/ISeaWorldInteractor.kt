package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldInteractor {

    fun getFieldData(): InitDataDto

    fun getNextDataObservable(delay: Long): Observable<CurrentStateDto>

    fun cleanDatabase()

    fun getCurrentPosition(): CurrentStateDto

    fun resetGame()

    fun getStatisticsObservable(): Observable<StatisticsDto>
}