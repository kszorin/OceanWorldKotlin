package ru.kszorin.seaworldkotlin.use_cases

import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import ru.kszorin.seaworldkotlin.use_cases.dto.InitDataDto
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import rx.Observable

/**
 * Created on 28.03.2018.
 */
class SeaWorldInteractor(val seaWorldRepository: ISeaWorldRepository): ISeaWorldInteractor {

    override fun getInitData(): InitDataDto {
        return seaWorldRepository.getFieldParameters()
    }

    override fun getResetGameObservable(): Observable<CurrentStateDto> {

        return Observable.create({ subscriber ->
            seaWorldRepository.cleanDatabase()
            seaWorldRepository.resetGame()

            subscriber.onNext(seaWorldRepository.getCurrentState())
            subscriber.onCompleted()
        })
    }

    override fun getNextStepObservable(delay: Long): Observable<CurrentStateDto> {
        return seaWorldRepository.getNextStepObservable(delay)
    }

    override fun getStatisticsObservable(): Observable<StatisticsDto> {
        return Observable.create({ subscriber ->
            subscriber.onNext(seaWorldRepository.getStatistics())
            subscriber.onCompleted()
        })
    }
}