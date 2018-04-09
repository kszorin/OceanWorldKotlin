package ru.kszorin.seaworldkotlin.ui

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.kszorin.seaworldkotlin.R
import ru.kszorin.seaworldkotlin.databinding.ActivityStatisticsBinding
import ru.kszorin.seaworldkotlin.presenters.IStatisticsView
import ru.kszorin.seaworldkotlin.presenters.StatisticsPresenter
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsDto
import java.io.Serializable

class StatisticsActivity : MvpAppCompatActivity(), IStatisticsView  {

    lateinit private var binding: ActivityStatisticsBinding

    private lateinit var adapter: StatisticsAdapter

    @InjectPresenter
    lateinit var presenter: StatisticsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_statistics, null, false)
        setContentView(binding.root)

        binding.recyclerViewStatistics.layoutManager = LinearLayoutManager(this)

    }

    @ProvidePresenter
    fun providePresenter(): StatisticsPresenter {
        return StatisticsPresenter(getIntent().getSerializableExtra(STATISTICS_KEY) as StatisticsDto)
    }

    override fun displayStatistics(statisticsDto: StatisticsDto) {
        adapter = StatisticsAdapter(statisticsDto.statisticsRecordList)
        binding.recyclerViewStatistics.adapter = adapter
    }

    companion object {
        private val TAG = "StatisticsActivity"
        private val STATISTICS_KEY = "StatisticsActivity"

        fun createIntent(context: Context, statisticsDto: Serializable): Intent {
            val intent = Intent(context, StatisticsActivity::class.java)
            intent.putExtra(STATISTICS_KEY, statisticsDto)
            return intent
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        binding.recyclerViewStatistics.adapter = null
        super.onDestroy()
    }
}
