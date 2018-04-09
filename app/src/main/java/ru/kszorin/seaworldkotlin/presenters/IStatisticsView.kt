package ru.kszorin.seaworldkotlin.presenters

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto

/**
 * Created on 05.04.2018.
 */
@StateStrategyType(SkipStrategy::class)
interface IStatisticsView : MvpView {

    /**
     * Displays statistics data.
     */
    fun displayStatistics(statisticsDto: StatisticsDto)
}