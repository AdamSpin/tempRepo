package com.example.myfinancepal.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinancepal.R
import com.example.myfinancepal.R.layout.fragment_createbudget
import kotlinx.android.synthetic.main.fragment_createbudget.view.*

class CreateBudgetFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createbudget)
    }
}