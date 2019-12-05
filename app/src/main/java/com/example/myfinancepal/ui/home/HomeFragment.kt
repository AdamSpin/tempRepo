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

        var checkFile = requireActivity().applicationContext.getFileStreamPath("budgetFile2")
        if(checkFile == null || !checkFile.exists()){
            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }

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
            val itemNameVal = bud["name"].toString() + ":"   //$" + bud["value"].toString()
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

        root.button_bud_Clear.setOnClickListener {
            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }

        root.test_bud_button.setOnClickListener {
            var ffileInputStream: FileInputStream? = null
            ffileInputStream = requireActivity().applicationContext.openFileInput("budgetFile2")
            var iinputStreamReader: InputStreamReader = InputStreamReader(ffileInputStream)
            val bbufferedReader: BufferedReader = BufferedReader(iinputStreamReader)
            val sstringBuilder: StringBuilder = StringBuilder()
            var ttext: String? = null
            while ({ ttext = bbufferedReader.readLine(); ttext }() != null) {
                sstringBuilder.append(ttext)
            }
            d("Adam", sstringBuilder.toString())
        }

        root.budget_button.setOnClickListener {
            val intent = Intent(activity, CreateBudgetFragment::class.java)
            requireContext().startActivity(intent)
        }
        return root
    }
}