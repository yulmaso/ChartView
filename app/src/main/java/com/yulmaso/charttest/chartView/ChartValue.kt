package com.yulmaso.charttest.chartView

import java.util.*

/**
 *  Created by yulmaso
 *  Date: 15.04.21
 */
open class ChartValue(
    val value: Float,
    private val date: Calendar
): Comparable<ChartValue> {

    val year: Int = date.get(Calendar.YEAR)

    val monthStr: String = when(date.get(Calendar.MONTH)) {
        0 -> "янв"
        1 -> "фев"
        2 -> "мар"
        3 -> "апр"
        4 -> "май"
        5 -> "июн"
        6 -> "июл"
        7 -> "авг"
        8 -> "сен"
        9  -> "окт"
        10 -> "ноя"
        11 -> "дек"
        else -> throw IllegalStateException("Month can't be ${date.get(Calendar.MONTH)}")
    }

    override fun compareTo(other: ChartValue): Int {
        return date.compareTo(other.date)
    }
}