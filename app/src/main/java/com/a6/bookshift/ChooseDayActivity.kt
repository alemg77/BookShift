package com.a6.bookshift

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ChooseDayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_day)

        val calendarInstance = Calendar.getInstance()

        val year = calendarInstance.get(Calendar.YEAR)
        val month = calendarInstance.get(Calendar.MONTH)
        val day = calendarInstance.get(Calendar.DAY_OF_MONTH)

        val mDateSetListener =
            DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                Log.d("TAGGG", "mDateSetListener $datePicker, $i, $i1, $i2")
            }

        val datePickerDialog = DatePickerDialog(
            this,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mDateSetListener,
            year, month, day
        )

        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()

    }
}