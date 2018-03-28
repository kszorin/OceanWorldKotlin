package ru.kszorin.seaworldkotlin.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kszorin.seaworldkotlin.repositories.SeaWorldRepository
import ru.kszorin.seaworldkotlin.use_cases.ISeaWorldInteractor
import ru.kszorin.seaworldkotlin.use_cases.SeaWorldInteractor

/**
 * Created on 28.03.2018.
 */
@InjectViewState
class MainPresenter : MvpPresenter<IMainView>() {

    private var seaWorldInteractor : ISeaWorldInteractor = SeaWorldInteractor(SeaWorldRepository())

}