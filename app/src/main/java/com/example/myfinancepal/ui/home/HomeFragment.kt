package com.example.myfinancepal.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myfinancepal.R
import com.example.myfinancepal.ui.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_budgets.budget_button
import kotlinx.android.synthetic.main.fragment_budgets.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_budgets, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        root.budget_button.setOnClickListener {
            d("Adam", "Button pressed")
            val intent = Intent(activity, CreateBudgetFragment::class.java)
            requireContext().startActivity(intent)
        }
        return root
    }
}