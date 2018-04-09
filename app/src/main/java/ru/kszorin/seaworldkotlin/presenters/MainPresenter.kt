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

    private var nextStepObservable: Observable<CurrentStateDto>? = null
    private var compositeSubscription: CompositeSubscription? = null

    /**
     *  Enter point in Use cases layer.
     */
    private var seaWorldInteractor: ISeaWorldInteractor = SeaWorldInteractor(SeaWorldRepository())

    /**
     * Flag for blocking re-touch during life step.
     */
    private var isLifeStepInProgress = false

    override fun onFirstViewAttach() {
        Log.d(TAG, "onFirstViewAttach")
        super.onFirstViewAttach()

        registerSubscription(seaWorldInteractor.getResetGameObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentPosition ->
                    val initData = seaWorldInteractor.getInitData()
                    viewState.initField(initData.fieldSize, currentPosition.creaturesList)
                    nextStepObservable = seaWorldInteractor.getNextStepObservable(UPDATE_POSITIONS_DELAY)
                }))
    }

    fun onTouch() {
        if (!isLifeStepInProgress) {
            isLifeStepInProgress = true
            registerSubscription(nextStepObservable!!
                    .subscribeOn(Schedulers.computation())
                    .subscribe({ currentPosition -> viewState.updateField(currentPosition.creaturesList) },
                            { t -> },
                            {
                                //reset flag when life step was completed
                                isLifeStepInProgress = false }))
        }
    }

    fun onReset() {
        registerSubscription(seaWorldInteractor.getResetGameObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentPosition ->
                    viewState.updateField(currentPosition.creaturesList)
                    isLifeStepInProgress = false
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