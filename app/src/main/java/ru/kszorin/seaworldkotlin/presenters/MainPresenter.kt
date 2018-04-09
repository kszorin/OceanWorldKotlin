package ru.kszorin.seaworldkotlin.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kszorin.seaworldkotlin.repositories.SeaWorldRepository
import ru.kszorin.seaworldkotlin.use_cases.ISeaWorldInteractor
import ru.kszorin.seaworldkotlin.use_cases.SeaWorldInteractor
import ru.kszorin.seaworldkotlin.use_cases.dto.CurrentStateDto
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created on 28.03.2018.
 */
@InjectViewState
class MainPresenter : MvpPresenter<IMainView>() {
    private var obs: Observable<CurrentStateDto>? = null
    private var compositeSubscription: CompositeSubscription? = null

    private var seaWorldInteractor: ISeaWorldInteractor = SeaWorldInteractor(SeaWorldRepository())

    private var inProgressFlag = false

    override fun onFirstViewAttach() {
        Log.d(TAG, "onFirstViewAttach")
        super.onFirstViewAttach()

        registerSubscription(seaWorldInteractor.getResetGameObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentPosition ->
                    val initData = seaWorldInteractor.getInitData()
                    viewState.initField(initData.fieldSize.first, initData.fieldSize.second, currentPosition.creaturesList)
                    obs = seaWorldInteractor.getNextStepObservable(UPDATE_POSITIONS_DELAY)
                }))
    }

    fun onTouch() {
        if (!inProgressFlag) {
            inProgressFlag = true
            registerSubscription(obs!!
                    .subscribeOn(Schedulers.computation())
                    .subscribe({ currentPosition -> viewState.updateWorld(currentPosition.creaturesList) },
                            { t -> },
                            { inProgressFlag = false }))
        }
    }

    fun onReset() {
        registerSubscription(seaWorldInteractor.getResetGameObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentPosition ->
                    viewState.updateWorld(currentPosition.creaturesList)
                    inProgressFlag = false
                }))
    }

    fun onStatisticsItem() {
        registerSubscription(seaWorldInteractor
                .getStatisticsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ statisticsDto -> viewState.openStatistics(statisticsDto) }))
    }

    private fun registerSubscription(subscription: Subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription!!.add(subscription)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        clearSubscription()
        super.onDestroy()
    }

    protected fun clearSubscription() {
        if (compositeSubscription != null) {
            compositeSubscription!!.unsubscribe()
            compositeSubscription = null
        }
    }

    companion object {
        private val TAG = "MainPresenter"
        private val UPDATE_POSITIONS_DELAY = 0L
    }
}