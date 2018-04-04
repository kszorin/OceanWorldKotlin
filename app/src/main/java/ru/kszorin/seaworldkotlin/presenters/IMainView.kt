package ru.kszorin.seaworldkotlin.presenters

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData


/**
 * Created on 28.03.2018.
 */
@StateStrategyType(SkipStrategy::class)
interface IMainView: MvpView {

    fun initField(fieldSizeX: Int, fieldSizeY: Int, creaturesList: List<CreatureStepData>)

    fun updateWorld(creaturesList: List<CreatureStepData>)
}