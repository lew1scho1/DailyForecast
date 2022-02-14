package com.lewischoi.dailyforecast.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lewischoi.dailyforecast.*
import com.lewischoi.dailyforecast.databinding.ActivityMainBinding

class ForecastDetailsActivity : AppCompatActivity() {
    fun easyToast(msg: String)  {
       Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManager = TempDisplaySettingManager(this)
        setTitle(R.string.forecast_details)

        val tempText = findViewById<TextView>(R.id.tempText2)
        val descriptionText = findViewById<TextView>(R.id.descriptionText2)

        val temp = intent.getFloatExtra("key_temp", 0f)
        val description = intent.getStringExtra(("key_description"))

        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = "$description"
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
}