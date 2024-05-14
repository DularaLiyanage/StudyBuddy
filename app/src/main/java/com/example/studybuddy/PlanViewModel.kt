package com.example.studybuddy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.studybuddy.Plan
import com.example.studybuddy.PlansDatabaseHelper

class PlanViewModel(application: Application) : AndroidViewModel(application) {
    private val db: PlansDatabaseHelper = PlansDatabaseHelper(application)

    private val _plans = MutableLiveData<List<Plan>>()
    val plans: LiveData<List<Plan>> = _plans

    init {
        loadPlans()
    }

    fun loadPlans() {
        _plans.value = db.getAllPlans()
    }

    fun insertPlan(plan: Plan) {
        db.insertPlan(plan)
        loadPlans()
    }

    fun updatePlan(plan: Plan) {
        db.updatePlan(plan)
        loadPlans()
    }

    fun deletePlan(planId: Int) {
        db.deletePlan(planId)
        loadPlans()
    }

    fun searchPlans(query: String) {
        _plans.value = db.searchPlansByTitle(query)
    }
}
