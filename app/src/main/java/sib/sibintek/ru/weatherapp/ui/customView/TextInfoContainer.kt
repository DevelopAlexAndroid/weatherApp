package sib.sibintek.ru.weatherapp.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.text_info_container.view.*
import sib.sibintek.ru.weatherapp.R

class TextInfoContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.text_info_container, this, true)
        orientation = VERTICAL
        val at = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextInfoContainer,
            0, 0
        )

        title.text = at.getString(R.styleable.TextInfoContainer_containerName)
    }

    fun setData(infoTex: String){
        info.text = infoTex
        container.visibility = View.VISIBLE
    }

}