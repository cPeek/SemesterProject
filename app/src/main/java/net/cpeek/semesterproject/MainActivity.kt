package net.cpeek.semesterproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import net.cpeek.semesterproject.data.Team
import net.cpeek.semesterproject.db.DBManager
import net.cpeek.semesterproject.db.HockeyDB

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var selectedTeam: Team? = null
    private var teams: Array<Team> = emptyArray()

    private lateinit var sharedPrefs: SharedPreferences
    val db: HockeyDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = this.getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)

        teams = DBManager.getDB(this).teamDao().getAllTeams()

        val adapter:ArrayAdapter<Team> = ArrayAdapter(this, android.R.layout.simple_spinner_item, teams)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        team_spinner.adapter = adapter
        team_spinner.onItemSelectedListener = this


        select_button.setOnClickListener {
            val intent = Intent(this, MenuActivity()::class.java)

            startActivity(intent)
        }

        editTeams_button.setOnClickListener {
            val intent = Intent(this, TeamsActivity()::class.java)

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        teams = DBManager.getDB(this).teamDao().getAllTeams()
    }

    override fun onItemSelected(spinner: AdapterView<*>?, p1: View?, pos: Int, id: Long) {
        selectedTeam = spinner?.selectedItem as Team
        with(sharedPrefs.edit()){
            putInt("team_id", selectedTeam!!.id)
            putString("team_name", selectedTeam!!.teamName)
            apply()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}