package com.yulmaso.charttest

import android.content.res.Resources

/**
 *  Created by yulmaso
 *  Date: 15.04.21
 */
val Int.dpToPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dpToIntPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()