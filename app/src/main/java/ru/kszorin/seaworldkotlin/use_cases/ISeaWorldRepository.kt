package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
interface ISeaWorldRepository {

    /**
     * Method for getting field parameters.
     * @return DTO with field parameters.
     */
    fun getFieldParameters(): InitDataDto

    /**
     * Method cleans game database.
     */
    fun cleanDatabase()

    /**
     * Method resets game data.
     */
    fun resetGame()

    /**
     * Method for getting current game state (the creatures location on the playing field).
     * @return DTO with current state data.
     */
    fun getCurrentState(): CurrentStateDto

    /**
     * Method to retrieve observable, which emits data after each animal's step.
     * @param delay pause in ms after each animal's step.
     * @return observable which emits DTO with current state data.
     */
    fun getNextStepObservable(delay: Long): Observable<CurrentStateDto>

    /**
     * Method for getting data from database.
     * @return DTO with statistics data from database.
     */
    fun getStatistics(): StatisticsDto

}