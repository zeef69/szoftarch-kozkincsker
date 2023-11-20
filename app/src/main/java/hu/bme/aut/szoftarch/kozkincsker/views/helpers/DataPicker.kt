package hu.bme.aut.szoftarch.kozkincsker.views.helpers


import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import hu.bme.aut.szoftarch.kozkincsker.R
import java.util.Calendar

@Composable
fun DatePicker(
    fromToday: Boolean,
    context: Context,
    datePickerState: Boolean,
    setDatePickerState: (Boolean) -> Unit,
    dateInput: String,
    onValueChange: (String) -> Unit
) {
    val myCalendar = Calendar.getInstance()

    Button(
        content = {
            if (dateInput == "")
                Text(
                    text = "${myCalendar.get(Calendar.YEAR)}-" +
                            "${myCalendar.get(Calendar.MONTH) + 1}-" +
                            "${myCalendar.get(Calendar.DAY_OF_MONTH)}"
                )
            else
                Text(
                    text = dateInput
                )
        },
        onClick = { setDatePickerState(true) },
    )

    if (datePickerState) {
        val datePickerDialog = DatePickerDialog(
            context,
            R.style.DatePickerTheme,
            { _: DatePicker, Year: Int, Month: Int, Day: Int ->
                onValueChange("$Year-${Month + 1}-$Day")
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        if (fromToday)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()

        datePickerDialog.show()

        setDatePickerState(false)
    }
}