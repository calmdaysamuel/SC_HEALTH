package com.calmday.schealth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rView = view.findViewById(R.id.foodDetails)
//        addButton = view.findViewById(R.id.addFood)
//        addButton.setOnClickListener {
//            addFood()
//        }



        rView.adapter = adapter
        rView.layoutManager = LinearLayoutManager(view.context)
        loadData()
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                LogsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    lateinit var rView: RecyclerView
    lateinit var addButton: Button
    var adapter: FoodAdapter = FoodAdapter(mutableListOf())

    fun loadData() {
        var foods: List<Food> = listOf()

        val db = Room.databaseBuilder(
            activity!!.applicationContext,
            AppDatabase::class.java, "calorieDb.v1"
        ).allowMainThreadQueries().build()

        val foodDao = db.foodDao()
        foods = foodDao.getAll()
        adapter.addAll(foods.toMutableList())


    }
    fun addFood() {
        val intent = Intent(activity, RecordFoodActivity::class.java)
        ContextCompat.startActivity(
            activity!!.baseContext,
            intent,
            null
        )
    }
}