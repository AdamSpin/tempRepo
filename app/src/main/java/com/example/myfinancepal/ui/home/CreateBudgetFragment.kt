package com.example.myfinancepal.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinancepal.R
import com.example.myfinancepal.R.layout.fragment_createbudget
import kotlinx.android.synthetic.main.fragment_createbudget.*
import kotlinx.android.synthetic.main.fragment_createbudget.view.*
import org.json.JSONArray
import org.json.JSONObject

class CreateBudgetFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createbudget)

        confirm_budget.setOnClickListener {
            val budNew = JSONObject()
            val budgetList = JSONArray()
            budNew.put("name", field_budget_name.text)
            budNew.put("amount", field_budget_amt.text)
            budgetList.put(budNew)
            val item = budgetList.getJSONObject(0)
            val budName = item["name"]
            val budAmt = item["amount"]
            Toast.makeText(this, "Name: $budName Amount: $budAmt", Toast.LENGTH_SHORT).show()
        }
    }
}