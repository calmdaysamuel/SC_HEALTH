package com.calmday.schealth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    fun clearData() {

        val db = Room.databaseBuilder(
            activity!!.applicationContext,
            AppDatabase::class.java, "calorieDb.v1"
        ).allowMainThreadQueries().build()

        val foodDao = db.foodDao()
        foodDao.deleteAll()
    }

    fun addData() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loadData()
        view.findViewById<Button>(R.id.clear).setOnClickListener {
            clearData()
        }
//        view.findViewById<Button>(R.id.add).setOnClickListener {
//            addData()
//        }

        view.findViewById<TextView>(R.id.av).text = avg.toInt().toString()
        view.findViewById<TextView>(R.id.max).text = max.toString()
        view.findViewById<TextView>(R.id.min).text = min.toString()
        super.onViewCreated(view, savedInstanceState)
    }

    fun loadData() {
        var foods: List<Food> = listOf()

        val db = Room.databaseBuilder(
            activity!!.applicationContext,
            AppDatabase::class.java, "calorieDb.v1"
        ).allowMainThreadQueries().build()

        val foodDao = db.foodDao()
        foods = foodDao.getAll()

        for ( food in foods) {
            var cal = food.calories.toDouble()
           if ( cal > max) {
               this.max = cal
           }
           if (cal < min) {
               this.min = cal
            }
            this.avg += cal
        }

        this.avg = avg/foods.size

    }

    var max = 0.0
    var min = 0.0
    var avg = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}