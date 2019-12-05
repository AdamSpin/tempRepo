package com.example.myfinancepal.ui.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinancepal.R
import com.example.myfinancepal.ui.home.CreateBudgetFragment
import com.example.myfinancepal.ui.home.CreateTransactionFragment
import kotlinx.android.synthetic.main.fragment_budgets.view.*
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_transactions, container, false)

        /*val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        var checkFile = requireActivity().applicationContext.getFileStreamPath("transactionFile2")
        if(checkFile == null || !checkFile.exists()){
            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("transactionFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }

        var fileInputStream: FileInputStream? = null
        fileInputStream = requireActivity().applicationContext.openFileInput("transactionFile2")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        val fileString = stringBuilder.toString()
        val transArray = JSONArray(fileString)
        var lastId = root.transaction_button.id

        for(i in 0 until transArray.length()){
            val bud = transArray.getJSONObject(i)
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
            root.transLayout.addView(item)
            lastId = i
        }

        root.button_trans_Clear.setOnClickListener {
            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("transactionFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }

        root.test_trans_button.setOnClickListener {
            var testInputStream: FileInputStream? = null
            testInputStream = requireActivity().applicationContext.openFileInput("transactionFile2")
            var testStreamReader: InputStreamReader = InputStreamReader(testInputStream)
            val testReader: BufferedReader = BufferedReader(testStreamReader)
            val testBuilder: StringBuilder = StringBuilder()
            var temp: String? = null
            while ({ temp = testReader.readLine(); temp }() != null) {
                testBuilder.append(temp)
            }
            Log.d("Adam", testBuilder.toString())
        }

        root.transaction_button.setOnClickListener {
            val intent = Intent(activity, CreateTransactionFragment::class.java)
            requireContext().startActivity(intent)
        }

        return root
    }
}