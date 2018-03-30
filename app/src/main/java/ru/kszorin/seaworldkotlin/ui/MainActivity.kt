package ru.kszorin.seaworldkotlin.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.kszorin.seaworldkotlin.R
import ru.kszorin.seaworldkotlin.databinding.ActivityMainBinding
import ru.kszorin.seaworldkotlin.presenters.IMainView
import ru.kszorin.seaworldkotlin.presenters.MainPresenter
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData

class MainActivity : MvpActivity(), IMainView {

    lateinit private var binding: ActivityMainBinding

    lateinit private var playingWorldView : PlayingWorldView

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(binding.getRoot())

        playingWorldView = binding.playingWorldView
    }

    override fun onPause() {
        super.onPause()
        playingWorldView.stopGame()
    }

    override fun initField(fieldSizeX: Int, fieldSizeY: Int) {
        playingWorldView.fieldSizeX = fieldSizeX
        playingWorldView.fieldSizeY = fieldSizeY
    }

    override fun drawWorld(creaturesList: List<CreatureStepData>) {
        playingWorldView.creaturesList = creaturesList
    }

    companion object {
        private val TAG = "MainActivity"

    }
}
