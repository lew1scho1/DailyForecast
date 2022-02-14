package com.lewischoi.dailyforecast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lewischoi.dailyforecast.databinding.ActivityMainBinding
import com.lewischoi.dailyforecast.details.ForecastDetailsActivity

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

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
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){   forecast ->
            showForecastDetails(forecast)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForecast>> {    forecastItems ->
            // update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        return when(item.itemId)    {
            R.id.tempDisplaySetting -> {
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showForecastDetails(forecast: DailyForecast)   {
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)
    }



}