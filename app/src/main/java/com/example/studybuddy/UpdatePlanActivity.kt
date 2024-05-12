package com.example.studybuddy

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studybuddy.databinding.ActivityUpdatePlanBinding
import java.util.Calendar

class UpdatePlanActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePlanBinding
    private lateinit var db: PlansDatabaseHelper
    private var planId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PlansDatabaseHelper(this)

        planId = intent.getIntExtra("Plan_id", -1)
        if(planId == -1){
            finish()
            return
        }

        val plan = db.getPlanByID(planId)
        binding.updateTitleEditText.setText(plan.title)
        binding.updateContentEditText.setText(plan.content)
        binding.updatePriorityEditText.setText(plan.priority)
        binding.updateDeadlineEditText.setText(plan.deadline)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val newPriority = binding.updatePriorityEditText.text.toString()
            val newDeadline = binding.updateDeadlineEditText.text.toString()
            val updatePlan = Plan(planId, newTitle, newContent, newPriority, newDeadline)
            db.updatePlan(updatePlan)
            finish()
            Toast.makeText(this, "Change Saved", Toast.LENGTH_SHORT).show()
        }

        binding.updateDeadlineEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.updateDeadlineEditText.setText(selectedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}