package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldInteractor {

    fun fieldInitialization(): InitDataDto

    fun getNextDataObservable(delay: Long): Observable<CurrentStateDto>

    fun getCurrentPosition(): CurrentStateDto

    fun resetGame()
}