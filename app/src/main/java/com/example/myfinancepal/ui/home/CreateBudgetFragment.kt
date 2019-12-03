package com.example.myfinancepal.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.myfinancepal.MainActivity
import com.example.myfinancepal.R
import com.example.myfinancepal.R.layout.fragment_createbudget
import kotlinx.android.synthetic.main.fragment_createbudget.*
import kotlinx.android.synthetic.main.fragment_createbudget.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class CreateBudgetFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createbudget)

        var fileInputStream: FileInputStream = openFileInput("budgetFile")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while({text = bufferedReader.readLine(); text}() != null){
            stringBuilder.append(text)
        }
        val tv_dynamic = TextView(this)
        tv_dynamic.textSize = 20f
        tv_dynamic.text = stringBuilder.toString()
        createBudgetLayout.addView(tv_dynamic)

        confirm_budget.setOnClickListener {
            val budNew = JSONObject()
            val budArray = JSONArray()
            budNew.put("name", field_budget_name.text)
            budNew.put("amount", field_budget_amt.text)
            budNew.put("current", 0)
            budArray.put(budNew)
            val budString = budArray.toString()

            //ITERATE THROUGH JSONARRAY with array.getJSONObject(i)

            val fileOutputStream : FileOutputStream = openFileOutput("budgetFile", Context.MODE_PRIVATE)
            fileOutputStream.write(budString.toByteArray())
            Toast.makeText(this, "Budget Created", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}