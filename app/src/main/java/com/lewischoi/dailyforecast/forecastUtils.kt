package com.lewischoi.dailyforecast

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting) : String  {
    return when (tempDisplaySetting)    {
        TempDisplaySetting.Fahrentheit -> String.format("%.2f Fº", temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f) * (5f / 9f)
            String.format("%.2f Cº", temp)
        }
    }
}

fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager)  {
    val dialogBuilder = AlertDialog.Builder( context)
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature unit to use for temperature display")
        .setPositiveButton("Fº")    {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrentheit)
        }
        .setNeutralButton("Cº") {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener   {
            //easyToast("Setting will take affecton app restart")
        }

    dialogBuilder.show()
}