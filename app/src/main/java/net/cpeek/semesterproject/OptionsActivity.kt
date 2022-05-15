package net.cpeek.semesterproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.prefs.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.prefs)

        val sharedPrefs = getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)

        val unitsChecked = sharedPrefs.getBoolean("metric", true)
        prefs_units.isChecked = !unitsChecked

        prefs_units.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                with(sharedPrefs.edit()){
                    putBoolean("metric", false)
                    apply()
                }
            } else {
                with(sharedPrefs.edit()){
                    putBoolean("metric", true)
                    apply()
                }
            }
        }
    }
}