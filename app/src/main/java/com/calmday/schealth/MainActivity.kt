package com.calmday.schealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var rView: RecyclerView
    lateinit var addButton: Button
    var adapter: FoodAdapter = FoodAdapter(mutableListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rView = findViewById(R.id.foodDetails)
        addButton = findViewById(R.id.addFood)
        addButton.setOnClickListener {
            addFood()
        }



        rView.adapter = adapter
        rView.layoutManager = LinearLayoutManager(this)
        loadData()
    }
    fun loadData() {
            var foods: List<Food> = listOf()

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "calorieDb.v1"
            ).allowMainThreadQueries().build()

            val foodDao = db.foodDao()
            foods = foodDao.getAll()
            adapter.addAll(foods.toMutableList())


    }
    fun addFood() {
        val intent = Intent(this, RecordFoodActivity::class.java)
        ContextCompat.startActivity(
            this,
            intent,
            null
        )
    }
}

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "calories") val calories: String
    );

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAll(): List<Food>

    @Insert
    fun insertAll(vararg users: Food)

    @Delete
    fun delete(user: Food)

    @Query("DELETE FROM food")
    fun deleteAll()

}

@Database(entities = [Food::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}
class FoodAdapter(var foods: MutableList<Food>): RecyclerView.Adapter<FoodAdapter.ViewHolder> () {
    inner class ViewHolder(movie: View) : RecyclerView.ViewHolder(movie) {
        val name = movie.findViewById<TextView>(R.id.foodName)
        val calories = movie.findViewById<TextView>(R.id.calCount)
    }
    fun addAll(foods: MutableList<Food>) {
        this.foods = foods;
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val foodView = inflater.inflate(R.layout.food_detail, parent, false)
        // Return a new holder instance
        return ViewHolder(foodView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods.get(position)
        holder.name.text = food.name
        holder.calories.text = food.calories
    }

    override fun getItemCount(): Int {
        return foods.size
    }
}