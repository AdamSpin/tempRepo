package com.example.myfinancepal.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinancepal.R
import com.example.myfinancepal.ui.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_budgets.*
import kotlinx.android.synthetic.main.fragment_budgets.view.*
import org.json.JSONArray
import java.io.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_budgets, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        //Check if file exists. If not, fill it with an empty JSONArray
//        var checkFile = File("budgetFile")
//        if(!checkFile.exists()){
//            val createFile : FileOutputStream = requireContext().openFileOutput("budgetFile", Context.MODE_PRIVATE)
//            createFile.write("[]".toByteArray())
//        }
//        var fileInputStream: FileInputStream = requireContext().openFileInput("budgetFile")
//        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
//        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
//        val stringBuilder: StringBuilder = StringBuilder()
//        var text: String? = null
//        while({text = bufferedReader.readLine(); text}() != null){
//            stringBuilder.append(text)
//        }
//        var array = stringBuilder.toString()
//        var budArray: JSONArray = JSONArray(array)

//        for(i in 0 until budArray.length()){
//            var bud = budArray.getJSONObject(i)
//            var item = TextView(activity)
//
//        }
        root.testText.text = "Changed"

        root.budget_button.setOnClickListener {
            val intent = Intent(activity, CreateBudgetFragment::class.java)
            requireContext().startActivity(intent)
        }
        return root
    }
}