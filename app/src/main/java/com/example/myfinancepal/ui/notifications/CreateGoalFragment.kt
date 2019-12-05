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
import com.example.myfinancepal.R.layout.fragment_creategoal
import kotlinx.android.synthetic.main.fragment_createbudget.*
import kotlinx.android.synthetic.main.fragment_creategoal.*
import kotlinx.android.synthetic.main.fragment_creategoal.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class CreateGoalFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_creategoal)

        var checkFile = applicationContext.getFileStreamPath("goalFile2")
        if(checkFile == null || !checkFile.exists()){
            var fileOutputStream : FileOutputStream = applicationContext.openFileOutput("goalFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }
        var fileInputStream: FileInputStream? = null
        fileInputStream = applicationContext.openFileInput("goalFile2")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        var fileString = stringBuilder.toString()
        var goalArray = JSONArray(fileString)

        //When confirm_goal is pressed, error check and create new goal
        confirm_goal.setOnClickListener {
            var nameIsGood = true
            var itemName = field_goal_name.text.toString().trim()
            var itemAmt = field_goal_amt.text.toString().trim()

            if(itemName.isEmpty()){
                Toast.makeText(this, "Goal must have a name", Toast.LENGTH_SHORT).show()
            }
            else{
                for(i in 0 until goalArray.length()){
                    val goal = goalArray.getJSONObject(i)
                    if(goal["name"] == itemName){
                        nameIsGood = false
                    }
                }
                if(!nameIsGood){
                    Toast.makeText(this, "Goal already exists", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (itemAmt.isEmpty()) {
                        Toast.makeText(this, "Goal must have an amount", Toast.LENGTH_SHORT)
                            .show()
                    } else if (itemAmt.toFloat() < 0 || itemAmt.toFloat() > 100000) {
                        Toast.makeText(this, "Goal amount out of range", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        var item = JSONObject()
                        item.put("name", itemName)
                        item.put("value", itemAmt)
                        goalArray.put(item)

                        var fileOutputStream: FileOutputStream =
                            applicationContext.openFileOutput("goalFile2", Context.MODE_PRIVATE)
                        fileOutputStream.write(goalArray.toString().toByteArray())

                        Toast.makeText(this, "Goal Created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            //Check if user entered a budget name
        }
    }
}