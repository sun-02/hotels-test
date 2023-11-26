package com.example.hotels_test.ui.core

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class IndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private val size: Float? = null
    private val distance: Float? = null
    private val paints: List<Paint> = listOf(
        Paint().apply {
            color = Color.parseColor("#FFFFFFFF")
            style = Paint.Style.FILL
        },
        Paint().apply {
            color = Color.parseColor("#FFFFFFFF")
            style = Paint.Style.FILL
        },
        Paint().apply {
            color = Color.parseColor("#FFFFFFFF")
            style = Paint.Style.FILL
        },
        Paint().apply {
            color = Color.parseColor("#FFFFFFFF")
            style = Paint.Style.FILL
        },
        Paint().apply {
            color = Color.parseColor("#FFFFFFFF")
            style = Paint.Style.FILL
        },
    )

    fun dpOrSpToPx(context: Context, dpOrSpValue: Float): Float {
        return dpOrSpValue * context.resources.displayMetrics.density
    }
}