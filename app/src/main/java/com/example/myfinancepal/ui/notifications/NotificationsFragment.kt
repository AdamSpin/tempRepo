package com.example.myfinancepal.ui.notifications

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinancepal.R
import com.example.myfinancepal.ui.home.CreateBudgetFragment
import com.example.myfinancepal.ui.home.CreateGoalFragment
import kotlinx.android.synthetic.main.fragment_budgets.view.*
import kotlinx.android.synthetic.main.fragment_goals.view.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_goals, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        var checkFile = requireActivity().applicationContext.getFileStreamPath("goalFile2")
        if(checkFile == null || !checkFile.exists()){
            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("goalFile2", Context.MODE_PRIVATE)
            fileOutputStream.write("[]".toByteArray())
        }

        var fileInputStream: FileInputStream? = null
        fileInputStream = requireActivity().applicationContext.openFileInput("goalFile2")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        val fileString = stringBuilder.toString()
        val goalArray = JSONArray(fileString)
        var lastId = root.goal_button.id

        for(i in 0 until goalArray.length()){
            val bud = goalArray.getJSONObject(i)
            var item = TextView(activity)
            val itemNameVal = bud["name"].toString() + ":"
            item.text = itemNameVal
            item.id = i + 783
            item.textSize = 25f
            item.setPadding(15,0,0,0)
            item.setTextColor(Color.rgb(0,51,76))
            var params1 = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
            params1.addRule(RelativeLayout.BELOW, lastId)
            params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            item.layoutParams = params1
            root.goalLayout.addView(item)
            lastId = i + 783
        }

//        root.button_goal_Clear.setOnClickListener {
//            var fileOutputStream : FileOutputStream = requireActivity().applicationContext.openFileOutput("goalFile2", Context.MODE_PRIVATE)
//            fileOutputStream.write("[]".toByteArray())
//        }
//
//        root.test_goal_button.setOnClickListener {
//            var ffileInputStream: FileInputStream? = null
//            ffileInputStream = requireActivity().applicationContext.openFileInput("goalFile2")
//            var iinputStreamReader: InputStreamReader = InputStreamReader(ffileInputStream)
//            val bbufferedReader: BufferedReader = BufferedReader(iinputStreamReader)
//            val sstringBuilder: StringBuilder = StringBuilder()
//            var ttext: String? = null
//            while ({ ttext = bbufferedReader.readLine(); ttext }() != null) {
//                sstringBuilder.append(ttext)
//            }
//            Log.d("Adam", sstringBuilder.toString())
//        }


        root.goal_button.setOnClickListener {
            Log.d("Adam", "Goal Button pressed")
            val intent = Intent(activity, CreateGoalFragment::class.java)
            requireContext().startActivity(intent)
        }

        return root
    }
}