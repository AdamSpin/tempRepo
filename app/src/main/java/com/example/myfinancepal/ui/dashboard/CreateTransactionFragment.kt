package com.example.myfinancepal.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinancepal.R
import com.example.myfinancepal.R.layout.fragment_createtransaction
import kotlinx.android.synthetic.main.fragment_createbudget.view.*

class CreateTransactionFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_createtransaction)
    }
}