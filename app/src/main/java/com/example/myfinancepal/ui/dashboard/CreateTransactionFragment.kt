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
import com.example.myfinancepal.R.layout.fragment_createtransaction
import kotlinx.android.synthetic.main.fragment_createbudget.*
import kotlinx.android.synthetic.main.fragment_createbudget.view.*
import kotlinx.android.synthetic.main.fragment_createtransaction.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class CreateTransactionFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createtransaction)

        //Check if file exists. If not, fill it with an empty JSONArray
        var checkFile = File("transactionFile")
        if(!checkFile.exists()){
            val tempDelete : FileOutputStream = openFileOutput("transactionFile", Context.MODE_PRIVATE)
            tempDelete.write("[]".toByteArray())
        }

        //Read the file and create a JSONArray object from its contents
        var fileInputStream: FileInputStream = openFileInput("transactionFile")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while({text = bufferedReader.readLine(); text}() != null){
            stringBuilder.append(text)
        }
        var array = stringBuilder.toString()
        var transArray: JSONArray = JSONArray(array)

        //When transaction_budget button is clicked, error check and create new transaction
        confirm_transaction.setOnClickListener {
            var nameIsGood = true
            var inputTransactionName = field_transaction_name.text.toString().trim()
            var inputTransactionAmt = field_transaction_amt.text.toString().trim()

            //Check if user entered a transaction name
            if(inputTransactionName.isEmpty()){
                Toast.makeText(this, "Transaction must have a name", Toast.LENGTH_SHORT).show()
            }
            else{
                //Check if user entered a repeated transaction name
                for(i in 0 until transArray.length()){
                    val trans = transArray.getJSONObject(i)
                    if(trans["name"] == inputTransactionName){
                        nameIsGood = false
                    }
                }
                if(!nameIsGood){
                    Toast.makeText(this, "Transaction already exists", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(inputTransactionAmt.isEmpty()){
                        Toast.makeText(this, "Transaction must have an amount", Toast.LENGTH_SHORT).show()
                    }
                    else if(inputTransactionAmt.toFloat() < 0 || inputTransactionAmt.toFloat() > 100000){
                        Toast.makeText(this, "Transaction amount out of range", Toast.LENGTH_LONG).show()
                    }
                    else{
                        val transNew = JSONObject()
                        transNew.put("name", field_transaction_name.text)
                        transNew.put("amount", field_transaction_amt.text)
                        transNew.put("current", 0)
                        transArray.put(transNew)
                        val transString = transArray.toString()

                        //Iterate through JSONARRAY with array.getJSONObject(i)

                        val fileOutputStream : FileOutputStream = openFileOutput("transactionFile", Context.MODE_PRIVATE)
                        fileOutputStream.write(transString.toByteArray())
                        Toast.makeText(this, "Transaction Created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}