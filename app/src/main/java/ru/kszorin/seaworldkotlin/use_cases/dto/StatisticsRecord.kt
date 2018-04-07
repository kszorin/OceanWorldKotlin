package ru.kszorin.seaworldkotlin.use_cases.dto

import java.io.Serializable

/**
 * Created on 05.04.2018.
 */
data class StatisticsRecord(val id: Int,
                            val species: String,
                            val age: Int,
                            val isAlive: Boolean,
                            val childernNumber: Int,
                            val eatenNumber: Int): Serializable