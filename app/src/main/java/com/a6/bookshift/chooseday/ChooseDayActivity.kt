package com.a6.bookshift.chooseday

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.a6.bookshift.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ChooseDayActivity : AppCompatActivity() {

    private val dataBase = FirebaseFirestore.getInstance()

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

                dataBase.collection("calendario")
                    .document("$i2-$i1-$i")
                    .set(
                        hashMapOf(
                            "enable" to "1",
                            "12" to 5,
                            "14" to 5,
                            "16" to 5,
                            "18" to 2,
                            "30" to 1
                        )
                    )
                    .addOnCompleteListener {
                        Log.d("TAGGG", "Guardamos en Firestore")
                    }
                    .addOnFailureListener {
                        Log.d("TAGGG", "Error guardando en Firestore")
                    }
            }

        val datePickerDialog = DatePickerDialog(
            this,
            android.R.style.Theme_DeviceDefault_Dialog,
            mDateSetListener,
            year, month, day
        )

        val now = System.currentTimeMillis()
        val min = now + (1000 * 60 * 60 * 24 * 1)
        val max = now + (1000 * 60 * 60 * 24 * 20)

        datePickerDialog.datePicker.minDate = min
        datePickerDialog.datePicker.maxDate = max

        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()

    }
}