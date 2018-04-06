package ru.kszorin.seaworldkotlin.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kszorin.seaworldkotlin.R
import ru.kszorin.seaworldkotlin.databinding.StatisticsRecordBinding
import ru.kszorin.seaworldkotlin.use_cases.dto.StatisticsRecord

/**
 * Created on 06.04.2018.
 */
class StatisticsAdapter(val statisticsRecordList: List<StatisticsRecord>): RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>() {

    override fun getItemCount(): Int {
        return statisticsRecordList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StatisticsViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.statistics_record, parent, false)
        return StatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder?, position: Int) {
        holder!!.binding.apply {
            id.text = statisticsRecordList[position].id.toString()
            species.text = statisticsRecordList[position].species
            age.text = statisticsRecordList[position].age.toString()
            isAlive.isChecked = statisticsRecordList[position].isAlive
            childrenNumber.text = statisticsRecordList[position].childernNumber.toString()
            eatenNumber.text = statisticsRecordList[position].eatenNumber.toString()
        }

    }



    class StatisticsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = DataBindingUtil.bind<StatisticsRecordBinding>(itemView)
    }
}