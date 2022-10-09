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
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstFragment=LogsFragment()
        val secondFragment=DashboardFragment()
        val thirdFragment=RecordFoodFragment()


        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    setCurrentFragment(firstFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logs->setCurrentFragment(firstFragment)
                R.id.dashboard->setCurrentFragment(secondFragment)
                R.id.addFood->setCurrentFragment(thirdFragment)

            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_main_nav_host_fragment,fragment)
            commit()
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