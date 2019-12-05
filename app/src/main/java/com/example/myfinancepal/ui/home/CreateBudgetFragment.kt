package com.example.myfinancepal.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
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
import org.w3c.dom.Text
import java.io.*

class CreateBudgetFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createbudget)

        var checkFile = applicationContext.getFileStreamPath("budgetFile2")
        if(checkFile == null || !checkFile.exists()){
            var fileOutputStream : FileOutputStream = applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }
        var fileInputStream: FileInputStream? = null
        fileInputStream = applicationContext.openFileInput("budgetFile2")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        var fileString = stringBuilder.toString()
        var budgetArray = JSONArray(fileString)

        confirm_budget.setOnClickListener {
            var nameIsGood = true
            var itemName = field_budget_name.text.toString().trim()
            var itemAmt = field_budget_amt.text.toString().trim()

            if(itemName.isEmpty()){
                Toast.makeText(this, "Budget must have a name", Toast.LENGTH_SHORT).show()
            }
            else{
                for(i in 0 until budgetArray.length()){
                    val bud = budgetArray.getJSONObject(i)
                    if(bud["name"] == itemName){
                        nameIsGood = false
                    }
                }
                if(!nameIsGood){
                    Toast.makeText(this, "Budget already exists", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (itemAmt.isEmpty()) {
                        Toast.makeText(this, "Budget must have an amount", Toast.LENGTH_SHORT)
                            .show()
                    } else if (itemAmt.toFloat() < 0 || itemAmt.toFloat() > 100000) {
                        Toast.makeText(this, "Budget amount out of range", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        var item = JSONObject()
                        item.put("name", itemName)
                        item.put("value", itemAmt)
                        budgetArray.put(item)

                        var fileOutputStream: FileOutputStream =
                            applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
                        fileOutputStream.write(budgetArray.toString().toByteArray())

                        Toast.makeText(this, "Budget Created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}