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
import rx.functions.Action1
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

        registerSubscription(Observable.create(Observable.OnSubscribe<CurrentStateDto> { subscriber ->
            seaWorldInteractor.cleanDatabase()
            seaWorldInteractor.resetGame()
            subscriber.onNext(seaWorldInteractor.getCurrentPosition())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Action1 { currentPosition ->
                    val initData = seaWorldInteractor.getFieldData()
                    viewState.initField(initData.sizeX, initData.sizeY, currentPosition.creaturesList)
                    obs = seaWorldInteractor.getNextDataObservable(UPDATE_POSITIONS_DELAY)
                }))
    }

    fun onTouch() {
        Log.d(TAG, "onTouch")
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

        registerSubscription(Observable.create(Observable.OnSubscribe<CurrentStateDto> { subscriber ->
            seaWorldInteractor.cleanDatabase()
            seaWorldInteractor.resetGame()
            inProgressFlag = false
            subscriber.onNext(seaWorldInteractor.getCurrentPosition())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Action1 { currentPosition ->
                    viewState.updateWorld(currentPosition.creaturesList)
                }))
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
        private val TAG = "MainPresenter"
        private val UPDATE_POSITIONS_DELAY = 0L
    }
}