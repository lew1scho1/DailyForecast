package com.lewischoi.dailyforecast

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lewischoi.dailyforecast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val zipcodeEditText: EditText = binding.zipcodeEditText
        val enterButton: Button = binding.enterButton

        enterButton.setOnClickListener {

            val zipcode: String = zipcodeEditText.text.toString()
            Toast.makeText(this, zipcode, Toast.LENGTH_LONG).show()
        }


    }
}