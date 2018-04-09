package ru.kszorin.seaworldkotlin.presenters

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto


/**
 * Created on 28.03.2018.
 */
@StateStrategyType(SkipStrategy::class)
interface IMainView: MvpView {

    /**
     * Initializes field and places creatures on the field.
     */
    fun initField(fieldSize: Pair<Int, Int>, creaturesList: List<CreatureStepData>)

    /**
     * Updates field after each animal's step.
     */
    fun updateField(creaturesList: List<CreatureStepData>)

    /**
     * Updates field after each animal's step.
     */
    fun openStatistics(statisticsDto: StatisticsDto)
}