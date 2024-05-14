package com.example.studybuddy

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studybuddy.databinding.ActivityMainBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var plansAdapter: PlansAdapter
    private lateinit var viewModel: PlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(PlanViewModel::class.java)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plansAdapter = PlansAdapter(emptyList(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = plansAdapter

        viewModel.plans.observe(this, Observer { plans ->
            plansAdapter.refreshData(plans)
        })

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddStudyPlansActivity::class.java)
            startActivity(intent)
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchPlans(newText)
                } else {
                    viewModel.loadPlans()
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPlans()
    }
}

