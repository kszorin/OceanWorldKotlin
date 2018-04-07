package ru.kszorin.seaworldkotlin.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto

/**
 * Created on 05.04.2018.
 */
@InjectViewState
class StatisticsPresenter(val statisticsDto: StatisticsDto): MvpPresenter<IStatisticsView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.displayStatistics(statisticsDto)
    }
}