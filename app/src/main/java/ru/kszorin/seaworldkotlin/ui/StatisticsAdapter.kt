package ru.kszorin.seaworldkotlin.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kszorin.seaworldkotlin.R
import ru.kszorin.seaworldkotlin.databinding.StatisticsRecordBinding
import ru.kszorin.seaworldkotlin.databinding.StatisticsRecordHeaderBinding
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsRecord

/**
 * Created on 06.04.2018.
 */
class StatisticsAdapter(val statisticsRecordList: List<StatisticsRecord>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return statisticsRecordList.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (TYPE_HEADER == viewType) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.statistics_record_header, parent, false)
            return StatisticsHeaderViewHolder(view)
        } else {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.statistics_record, parent, false)
            return StatisticsViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (!(holder is StatisticsViewHolder)) {
            return
        }
        holder.binding.apply {
            id.text = statisticsRecordList[position-1].id.toString()
            species.text = statisticsRecordList[position-1].species
            age.text = statisticsRecordList[position-1].age.toString()
            isAlive.isChecked = statisticsRecordList[position-1].isAlive
            childrenNumber.text = statisticsRecordList[position-1].childrenNumber.toString()
            eatenNumber.text = statisticsRecordList[position-1].eatenNumber.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (0 == position) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    class StatisticsHeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = DataBindingUtil.bind<StatisticsRecordHeaderBinding>(itemView)
    }

    class StatisticsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = DataBindingUtil.bind<StatisticsRecordBinding>(itemView)
    }

    companion object {
        private val TYPE_HEADER = 0
        private val TYPE_ITEM = 1
    }
}