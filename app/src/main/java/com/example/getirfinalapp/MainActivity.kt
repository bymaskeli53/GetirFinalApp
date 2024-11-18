package com.example.getirfinalapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.getirfinalapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_primary)
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    fun updateToolbarPrice(price: Double) {
        findViewById<TextView>(R.id.tv_totalPrice)?.let { textView ->
            PriceAnimationHelper.animatePrice(
                textView = textView,
                startPrice = PriceFormatter.parsePrice(textView.text.toString()),
                endPrice = price,
                duration = 500
            )
        }
    }
}