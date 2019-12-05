package com.example.myfinancepal.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.myfinancepal.MainActivity
import com.example.myfinancepal.R
import com.example.myfinancepal.R.layout.fragment_createtransaction
import kotlinx.android.synthetic.main.fragment_createtransaction.*
import kotlinx.android.synthetic.main.fragment_createtransaction.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class CreateTransactionFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createtransaction)

        var checkBudgetFile = applicationContext.getFileStreamPath("budgetFile2")
        if(checkBudgetFile == null || !checkBudgetFile.exists()){
            var fileOutputStream : FileOutputStream = applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }
        var budgetInputStream: FileInputStream? = null
        budgetInputStream = applicationContext.openFileInput("budgetFile2")
        var budgetStreamReader: InputStreamReader = InputStreamReader(budgetInputStream)
        val budgetReader: BufferedReader = BufferedReader(budgetStreamReader)
        val budgetBuilder: StringBuilder = StringBuilder()
        var temp: String? = null
        while ({ temp = budgetReader.readLine(); temp }() != null) {
            budgetBuilder.append(temp)
        }
        var budgetString = budgetBuilder.toString()
        var budgetArray = JSONArray(budgetString)

        //Check if file exists. If not, fill it with an empty JSONArray
        var checkFile = applicationContext.getFileStreamPath("transactionFile2")
        if(checkFile == null || !checkFile.exists()){
            var fileOutputStream : FileOutputStream = applicationContext.openFileOutput("transactionFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }
        var fileInputStream: FileInputStream? = null
        fileInputStream = applicationContext.openFileInput("transactionFile2")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        var fileString = stringBuilder.toString()
        var transactionArray = JSONArray(fileString)

        //When transaction_budget button is clicked, error check and create new transaction
        confirm_transaction.setOnClickListener {
            var inputTransactionName = field_transaction_name.text.toString().trim()
            var inputTransactionAmt = field_transaction_amt.text.toString().trim()
            var inputTransactionBud = field_trans_budget.text.toString().trim()
            var inOrOut = depositOrWithdrawal.isChecked.toString()
            var budgetIsGood = false

            //Check if user entered a transaction name
            if(inputTransactionName.isEmpty() || inputTransactionAmt.isEmpty() || inputTransactionBud.isEmpty()){
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
            else{
                for(i in 0 until budgetArray.length()){
                    val bud = budgetArray.getJSONObject(i)
                    if(bud["name"] == inputTransactionBud){
                        budgetIsGood = true
                    }
                }
                if(!budgetIsGood){
                    Toast.makeText(this, "Budget does not exist", Toast.LENGTH_SHORT).show()
                }
                else if(inputTransactionAmt.toFloat() < 0 || inputTransactionAmt.toFloat() > 100000){
                    Toast.makeText(this, "Transaction amount out of range", Toast.LENGTH_LONG).show()
                }
                else{
                    val transNew = JSONObject()
                    transNew.put("name", inputTransactionName)
                    transNew.put("amount", inputTransactionAmt)
                    transNew.put("budget", inputTransactionBud)
                    transNew.put("inOrOut", inOrOut)
                    transactionArray.put(transNew)
                    val transString = transactionArray.toString()

                    val fileOutputStream : FileOutputStream = applicationContext.openFileOutput("transactionFile2", Context.MODE_PRIVATE)
                    fileOutputStream.write(transString.toByteArray())
                    updateBudget(inputTransactionBud, inputTransactionAmt, inOrOut)
                    Toast.makeText(this, "Transaction Created", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateBudget(budgetName: String, amount: String, deposit: String){
        var checkBudgetFile = applicationContext.getFileStreamPath("budgetFile2")
        if(checkBudgetFile == null || !checkBudgetFile.exists()){
            var fileOutputStream : FileOutputStream = applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }
        var budgetInputStream: FileInputStream? = null
        budgetInputStream = applicationContext.openFileInput("budgetFile2")
        var budgetStreamReader: InputStreamReader = InputStreamReader(budgetInputStream)
        val budgetReader: BufferedReader = BufferedReader(budgetStreamReader)
        val budgetBuilder: StringBuilder = StringBuilder()
        var temp: String? = null
        while ({ temp = budgetReader.readLine(); temp }() != null) {
            budgetBuilder.append(temp)
        }
        var budgetString = budgetBuilder.toString()
        var budgetArray = JSONArray(budgetString)

        for(i in 0 until budgetArray.length()){
            var bud = budgetArray.getJSONObject(i)
            if(bud["name"].toString() == budgetName){
                if(deposit == "false"){
                    var newBudAmount = bud["amount"].toString().toFloat() - amount.toFloat()
                    bud.put("amount", newBudAmount)
                }
                else{
                    var newBudAmount = bud["amount"].toString().toFloat() + amount.toFloat()
                    bud.put("amount", newBudAmount)
                }
            }
        }
        val budArrStr = budgetArray.toString()
        val fileOutputStream : FileOutputStream = applicationContext.openFileOutput("budgetFile2", Context.MODE_PRIVATE)
        fileOutputStream.write(budArrStr.toByteArray())
    }
}