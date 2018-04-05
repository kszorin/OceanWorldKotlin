package ru.kszorin.seaworldkotlin.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.kszorin.seaworldkotlin.R
import ru.kszorin.seaworldkotlin.databinding.ActivityMainBinding
import ru.kszorin.seaworldkotlin.presenters.IMainView
import ru.kszorin.seaworldkotlin.presenters.MainPresenter
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto

class MainActivity : MvpAppCompatActivity(), IMainView {

    lateinit private var binding: ActivityMainBinding

    lateinit private var playingWorldView: PlayingWorldView

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.contentMain!!.resetGameButton.setOnClickListener({
            presenter.onReset()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_statistics -> {
                presenter.onStatisticsItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initField(fieldSizeX: Int, fieldSizeY: Int, creaturesList: List<CreatureStepData>) {
        Log.d(TAG, "initField")
        playingWorldView = PlayingWorldView(this)
        playingWorldView.fieldSizeX = fieldSizeX
        playingWorldView.fieldSizeY = fieldSizeY
        playingWorldView.creaturesList = creaturesList
        playingWorldView.setOnTouchListener(View.OnTouchListener({ view, motionEvent ->
            val action = motionEvent.getAction()
            if (action == MotionEvent.ACTION_DOWN) {
                presenter.onTouch()
            }
            true
        }))
        binding.contentMain!!.playingWorldViewFrame.addView(playingWorldView)
    }

    override fun updateWorld(creaturesList: List<CreatureStepData>) {
        playingWorldView.creaturesList = creaturesList
    }

    override fun openStatistics(statisticsDto: StatisticsDto) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        super.onPause()
        presenter.onReset()
    }

    companion object {
        private val TAG = "MainActivity"

    }
}
