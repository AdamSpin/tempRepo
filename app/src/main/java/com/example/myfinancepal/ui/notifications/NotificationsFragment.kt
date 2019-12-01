package com.example.myfinancepal.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinancepal.R
import com.example.myfinancepal.ui.home.CreateBudgetFragment
import com.example.myfinancepal.ui.home.CreateGoalFragment
import kotlinx.android.synthetic.main.fragment_budgets.view.*
import kotlinx.android.synthetic.main.fragment_goals.view.*

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
        root.goal_button.setOnClickListener {
            Log.d("Adam", "Goal Button pressed")
            val intent = Intent(activity, CreateGoalFragment::class.java)
            requireContext().startActivity(intent)
        }

        return root
    }
}