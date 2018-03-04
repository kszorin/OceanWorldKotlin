package ru.kszorin.seaworldkotlin

/**
 * Created on 04.03.2018.
 */

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
        = Array(sizeOuter) { IntArray(sizeInner) }