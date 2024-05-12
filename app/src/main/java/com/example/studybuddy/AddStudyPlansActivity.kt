package com.example.studybuddy

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studybuddy.databinding.ActivityAddStudyPlansBinding
import java.util.Calendar

class AddStudyPlansActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudyPlansBinding
    private lateinit var db: PlansDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudyPlansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PlansDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val priority = binding.priorityEditText.text.toString()
            val deadline = binding.deadlineEditText.text.toString()
            val plan = Plan(0, title, content, priority, deadline)
            db.insertPlan(plan)
            finish()
            Toast.makeText(this, "Plan Saved", Toast.LENGTH_SHORT).show()
        }

        binding.deadlineEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                // Handle the selected date here
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.deadlineEditText.setText(selectedDate)
            },
            // Set the current date as the default date in the DatePickerDialog
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}