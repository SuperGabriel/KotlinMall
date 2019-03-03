package com.kotlin.goods

import android.widget.EditText
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton

/**
 * Create by Pidan
 */
fun NumberButton.getEditText(): EditText {
    return find(R.id.text_count)
}