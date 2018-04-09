package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldInteractor {

    /**
     * Method to retrieve data for game initialization.
     * @return DTO with game initialization data.
     */
    fun getInitData(): InitDataDto

    /**
     * Method to retrieve observable, which performs reset actions..
     * @return observable which emits current state DTO (after necessary actions).
     */
    fun getResetGameObservable(): Observable<CurrentStateDto>

    /**
     * Method to retrieve observable, which emits data after each animal's step.
     * @param delay pause in ms after each animal's step.
     * @return observable which emits DTO with current state data.
     */
    fun getNextStepObservable(delay: Long): Observable<CurrentStateDto>

    /**
     * Method to retrieve observable for getting statistics from database
     * @return observable which emits statistics DTO.
     */
    fun getStatisticsObservable(): Observable<StatisticsDto>
}