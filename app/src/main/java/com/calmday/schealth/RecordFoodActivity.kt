package com.calmday.schealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RecordFoodActivity : AppCompatActivity() {

    lateinit var foodField: EditText
    lateinit var calorieField: EditText
    lateinit var recordFoodButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_food)

        foodField = findViewById(R.id.foodField)
        calorieField = findViewById(R.id.calorieField)
        recordFoodButton = findViewById(R.id.recordFood)


        recordFoodButton.setOnClickListener {
            recordFood()
            val intent = Intent(this, MainActivity::class.java)
            ContextCompat.startActivity(
                this,
                intent,
                null
            )
        }
    }

    fun recordFood() {



            val food = foodField.text.toString()
            val cals = calorieField.text.toString()
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "calorieDb.v1"
            ).allowMainThreadQueries().build()
            db.foodDao().insertAll(Food(0, food, cals))


    }
}