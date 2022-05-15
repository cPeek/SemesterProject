package net.cpeek.semesterproject

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_edit_team.*
import net.cpeek.semesterproject.data.Team
import net.cpeek.semesterproject.db.*


class AddTeamActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_edit_team)

        addPlayer_save.setOnClickListener {

            var newTeamName = addPlayer_fName.text.toString()

            //TODO: make it possible to pick an image
            var newTeamImg = defaultImgByteArray(this)


            var newTeam: Team = Team(teamName = newTeamName, img = newTeamImg)


            Toast.makeText(this, "Team added.", Toast.LENGTH_SHORT).show()
            DBManager.getDB(this).teamDao().insert(newTeam)


            super.finish()
        }
    }
}