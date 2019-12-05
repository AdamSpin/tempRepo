package com.example.myfinancepal.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myfinancepal.R
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

//        var fileInputStream: FileInputStream = root.context.openFileInput("budgetFile")
//        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
//        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
//        val stringBuilder: StringBuilder = StringBuilder()
//        var text: String? = null
//        while({text = bufferedReader.readLine(); text}() != null){
//            stringBuilder.append(text)
//        }
//        var array = stringBuilder.toString()
//        var budArray: JSONArray = JSONArray(array)
//        var lastNumId = root.budget_button.id
//
//        for(i in 0 until budArray.length()){
//            val currentId = 2*i
//            var bud = budArray.getJSONObject(i)
//            var item = TextView(activity)
//            val itemNameVal = bud["name"].toString() + ":"
//            item.text = itemNameVal
//            item.id = currentId
//            item.textSize = 25f
//            item.setPadding(15,0,0,0)
//            item.setTextColor(Color.rgb(0,51,76))
//            var params1 = RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT)
//            params1.addRule(RelativeLayout.BELOW, lastNumId)
//            params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
//            item.layoutParams = params1
//
//            var itemAmt = TextView(activity)
//            val itemAmtVal = bud["current"].toString()
//            itemAmt.text = itemAmtVal
//            itemAmt.id = currentId + 1
//            itemAmt.textSize = 25f
//            itemAmt.setPadding(0,0,15,0)
//            itemAmt.setTextColor(Color.rgb(0,51,76))
//            var params2 = RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT)
//            params2.addRule(RelativeLayout.BELOW, lastNumId)
//            params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
//            itemAmt.layoutParams = params2
//
//            root.budgetLayout.addView(item)
//            root.budgetLayout.addView(itemAmt)
//
//            lastNumId = currentId
//        }

//        var textView = TextView(activity)
//        textView.text = "Hello World"
//        textView.textSize = 30f
//        textView.setPadding(0,0,15,0)
//        textView.setTextColor(Color.rgb(0,51, 76))
//        var params = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
//            RelativeLayout.LayoutParams.WRAP_CONTENT)
//        params.addRule(RelativeLayout.BELOW, root.budget_button.id)
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
//        textView.layoutParams = params
//        root.budgetLayout.addView(textView)
        var fileInputStream: FileInputStream? = null
        fileInputStream = requireActivity().applicationContext.openFileInput("budgetFile2")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        val fileString = stringBuilder.toString()
        val budgetArray = JSONArray(fileString)
        var lastId = root.budget_button.id

        for(i in 0 until budgetArray.length()){
            val bud = budgetArray.getJSONObject(i)
            var item = TextView(activity)
            val itemNameVal = bud["name"].toString() + ":"
            item.text = itemNameVal
            item.id = i
            item.textSize = 25f
            item.setPadding(15,0,0,0)
            item.setTextColor(Color.rgb(0,51,76))
            var params1 = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
            params1.addRule(RelativeLayout.BELOW, lastId)
            params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            item.layoutParams = params1
            root.budgetLayout.addView(item)
            lastId = i
        }

        root.button_Clear.setOnClickListener {
            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }

        root.test_button.setOnClickListener {
            var fileInputStream: FileInputStream? = null
            fileInputStream = requireActivity().applicationContext.openFileInput("budgetFile2")
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            d("Adam", stringBuilder.toString())
        }

        root.budget_button.setOnClickListener {
            val intent = Intent(activity, CreateBudgetFragment::class.java)
            requireContext().startActivity(intent)
        }
        return root
    }
}