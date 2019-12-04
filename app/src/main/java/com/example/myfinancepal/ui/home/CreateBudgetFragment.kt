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
import java.io.*

class CreateBudgetFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createbudget)

        //Read the file and create a JSONArray object from its contents
        var fileInputStream: FileInputStream = openFileInput("budgetFile")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while({text = bufferedReader.readLine(); text}() != null){
            stringBuilder.append(text)
        }
        var array = stringBuilder.toString()
        var budArray: JSONArray = JSONArray(array)

        //When confirm_budget button is clicked, error check and create new budget
        confirm_budget.setOnClickListener {
            var nameIsGood = true
            var inputBudgetName = field_budget_name.text.toString().trim()
            var inputBudgetAmt = field_budget_amt.text.toString().trim()

            //Check if user entered a budget name
            if(inputBudgetName.isEmpty()){
                Toast.makeText(this, "Budget must have a name", Toast.LENGTH_SHORT).show()
            }
            else{
                //Check if user entered a budget name that was not already taken
                for(i in 0 until budArray.length()){
                    val bud = budArray.getJSONObject(i)
                    if(bud["name"] == inputBudgetName){
                        nameIsGood = false
                    }
                }
                if(!nameIsGood){
                    Toast.makeText(this, "Budget already exists", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(inputBudgetAmt.isEmpty()){
                        Toast.makeText(this, "Budget must have an amount", Toast.LENGTH_SHORT).show()
                    }
                    else if(inputBudgetAmt.toFloat() < 0 || inputBudgetAmt.toFloat() > 100000){
                        Toast.makeText(this, "Budget amount out of range", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val budNew = JSONObject()
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
        }
    }
}