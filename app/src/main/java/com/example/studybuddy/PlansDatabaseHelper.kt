package com.example.studybuddy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlansDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val  DATABASE_NAME = "studyPlan.db"
        private const val  DATABASE_VERSION = 1
        private const val  TABLE_NAME = "allPlan"
        private const val  COLUMN_ID = "id"
        private const val  COLUMN_TITLE = "title"
        private const val  COLUMN_CONTENT = "content"
        private const val  COLUMN_PRIORITY = "priority"
        private const val  COLUMN_DEADLINE = "deadline"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT,$COLUMN_PRIORITY TEXT, $COLUMN_DEADLINE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertPlan(plan: Plan){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, plan.title)
            put(COLUMN_CONTENT, plan.content)
            put(COLUMN_PRIORITY, plan.priority)
            put(COLUMN_DEADLINE, plan.deadline)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllPlans(): List<Plan> {
        val plansList = mutableListOf<Plan>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
            val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))

            val plan = Plan(id, title, content, priority, deadline)
            plansList.add(plan)
        }
        cursor.close()
        db.close()
        return plansList
    }

    fun updatePlan(plan: Plan){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, plan.title)
            put(COLUMN_CONTENT, plan.content)
            put(COLUMN_PRIORITY, plan.priority)
            put(COLUMN_DEADLINE, plan.deadline)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(plan.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getPlanByID(planId: Int): Plan{
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $planId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
        val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))

        cursor.close()
        db.close()
        return Plan(id, title, content, priority, deadline)
    }

    fun deletePlan(planId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(planId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun searchPlansByTitle(query: String): List<Plan> {
        val plansList = mutableListOf<Plan>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_PRIORITY, COLUMN_DEADLINE),
            "$COLUMN_TITLE LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
            val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))

            val plan = Plan(id, title, content, priority, deadline)
            plansList.add(plan)
        }
        cursor.close()
        db.close()
        return plansList
    }
}