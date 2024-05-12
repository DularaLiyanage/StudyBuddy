package com.example.studybuddy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PlansAdapter(private var plans: List<Plan>, context: Context) : RecyclerView.Adapter<PlansAdapter.PlanViewHolder>() {

    private val db: PlansDatabaseHelper = PlansDatabaseHelper(context)

    class PlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView)
        val deadlineTextView: TextView = itemView.findViewById(R.id.deadlineTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_item, parent, false)
        return PlanViewHolder(view)
    }

    override fun getItemCount(): Int  = plans.size

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val Plan = plans[position]
        holder.titleTextView.text = Plan.title
        holder.contentTextView.text = Plan.content
        holder.priorityTextView.text = Plan.priority
        holder.deadlineTextView.text = Plan.deadline

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdatePlanActivity::class.java).apply{
                putExtra("Plan_id", Plan.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deletePlan(Plan.id)
            refreshData(db.getAllPlans())
            Toast.makeText(holder.itemView.context, "Plan Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newPlans: List<Plan>){
        plans = newPlans
        notifyDataSetChanged()
    }
}