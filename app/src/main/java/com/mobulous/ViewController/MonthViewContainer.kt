package com.mobulous.ViewController

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.mobulous.fitscope.databinding.CalenderHeaderLayBinding

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalenderHeaderLayBinding.bind(view).legendLayout


}