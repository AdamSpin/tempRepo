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
import kotlinx.android.synthetic.main.fragment_creategoal.*
import kotlinx.android.synthetic.main.fragment_creategoal.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class CreateGoalFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_creategoal)

        //Check if file exists. If not, fill it with an empty JSONArray
        var checkFile = File("goalFile")
        if(!checkFile.exists()){
            val tempDelete : FileOutputStream = openFileOutput("transactionFile", Context.MODE_PRIVATE)
            tempDelete.write("[]".toByteArray())
        }

        //Read the file and create a JASONArray object from its contents
        var fileInputStream: FileInputStream = openFileInput("goalFile")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while({text = bufferedReader.readLine(); text}() != null){
            stringBuilder.append(text)
        }
        var array = stringBuilder.toString()
        var goalArray: JSONArray = JSONArray(array)

        //When confirm_goal is pressed, error check and create new goal
        confirm_goal.setOnClickListener {
            var nameIsGood = true
            var inputGoalName = field_goal_name.text.toString().trim()
            var inputGoalAmt = field_goal_amt.text.toString().trim()

            //Check if user entered a budget name
        }
    }
}