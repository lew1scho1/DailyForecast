package com.lewischoi.dailyforecast

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lewischoi.dailyforecast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val zipcodeEditText: EditText = binding.zipcodeEditText
        val enterButton: Button = binding.enterButton

        enterButton.setOnClickListener {

            val zipcode: String = zipcodeEditText.text.toString()

            if( zipcode.length != 5)    {
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_LONG).show()
            }   else {
                forecastRepository.loadForecast(zipcode)
            }

        }

        val forecastList: RecyclerView = binding.forcastList
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter({
            val msg = getString(R.string.forecast_clicked_format, it.temp, it.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        })
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForecast>> {    forecastItems ->
            // update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)



    }
}