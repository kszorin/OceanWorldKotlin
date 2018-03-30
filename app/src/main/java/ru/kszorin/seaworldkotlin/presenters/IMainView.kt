package ru.kszorin.seaworldkotlin.presenters

import com.arellomobile.mvp.MvpView
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData


/**
 * Created on 28.03.2018.
 */

interface IMainView: MvpView {

    fun initField(fieldSizeX: Int, fieldSizeY: Int)

    fun drawWorld(creaturesList: List<CreatureStepData>)
}