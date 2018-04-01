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

    override fun attachView(view: IMainView?) {
        super.attachView(view)
        val initData = seaWorldInteractor.fieldInitialization()
        val currentPosition = seaWorldInteractor.getCurrentPosition()
        viewState.initField(initData.sizeX, initData.sizeY)
        viewState.drawWorld(currentPosition.creaturesList)

        obs = Observable.create(Observable.OnSubscribe<CurrentStateDto> { subscriber ->
            seaWorldInteractor.doNextStep(UPDATE_POSITIONS_DELAY)
            subscriber.onNext(seaWorldInteractor.getCurrentPosition())
            subscriber.onCompleted()
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun onTouch() {
        Log.d(TAG, "onTouch")
        registerSubscription( obs!!.subscribe({ currentPosition -> viewState.drawWorld(currentPosition.creaturesList) }))
    }

    fun onReset() {
        seaWorldInteractor.resetGame()
        val currentPosition = seaWorldInteractor.getCurrentPosition()
        viewState.drawWorld(currentPosition.creaturesList)
    }

    protected fun registerSubscription(subscription: Subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription!!.add(subscription)
    }

    override fun onDestroy() {
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
        val TAG = "MainPresenter"
        val UPDATE_POSITIONS_DELAY = 300L
    }
}