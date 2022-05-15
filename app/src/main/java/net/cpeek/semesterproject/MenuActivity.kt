package net.cpeek.semesterproject

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_menu.*
import net.cpeek.semesterproject.db.DBManager
import net.cpeek.semesterproject.db.byteArrayToBmp

class MenuActivity() : AppCompatActivity() {

    private var teamID = -2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if(intent.hasExtra("team_id")) {
            teamID = intent.getIntExtra("team_id", -1)
        }*/

        val sharedPrefs = this.getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)

        teamID = sharedPrefs.getInt("team_id", -1)
        val teamName = sharedPrefs.getString("team_name", "None")

        Log.println(Log.DEBUG, "hdb", "onCreate Team ID: $teamID")

        val teamImg: Bitmap = byteArrayToBmp(DBManager.getDB(this).teamDao().getTeamByID(teamID).img!!)
        //val teamName = intent.getStringExtra("team_name")
        setContentView(R.layout.main_menu)

        team_name_text.setText(teamName)
        mainMenu_img.setImageBitmap(teamImg)

        lines_button.setOnClickListener {
            var intent = Intent(this, LinesActivity()::class.java )
            startActivity(intent)
        }

        players_button.setOnClickListener {
            val intent = Intent(this, PlayersActivity()::class.java)
            //intent.putExtra("team", teamID)
            startActivity(intent)
        }

        pref_button.setOnClickListener {
            var intent = Intent(this, OptionsActivity()::class.java )
            startActivity(intent)
        }

        help_button.setOnClickListener {
            var intent = Intent(this, HelpActivity()::class.java )
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.println(Log.DEBUG, "hdb", "onResume Team ID: $teamID")
    }
}