package net.cpeek.semesterproject

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_edit_player.*
import kotlinx.android.synthetic.main.add_edit_team.addPlayer_fName
import kotlinx.android.synthetic.main.add_edit_team.addPlayer_save
import net.cpeek.semesterproject.data.Player
import net.cpeek.semesterproject.db.DBManager
import net.cpeek.semesterproject.db.defaultImg
import kotlin.math.roundToInt


class AddPlayerActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_edit_player)

        val sharedPrefs = this.getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)
        val teamID = sharedPrefs.getInt("team_id", -1)

        addPlayer_save.setOnClickListener {

            var fName = addPlayer_fName.text.toString()
            var lName = addPlayer_lName.text.toString()
            var num = addPlayer_number.text.toString()

            var position = addPlayer_position.selectedItem.toString()
            var hand = addPlayer_hand.selectedItem.toString()
            var bHand = true
            if(hand == "L") bHand = false

            var metric = sharedPrefs.getBoolean("metric", true)
            var height: Int = 0
            var weight: Int = 0

            // Accept height/weight input and convert to metric if needed
            if(addPlayer_height.text.toString() != ""){
                height = addPlayer_height.text.toString().toInt()
                if(metric == false){
                    height = (height * 2.54).roundToInt()
                }

            }
            if(addPlayer_weight.text.toString() != ""){
                weight = addPlayer_weight.text.toString().toInt()
                if(metric == false){
                    weight = (weight / 2.205).roundToInt()
                }
            }

            var birthYear = addPlayer_birthday.text.toString()

            //TODO: make it possible to pick an image
            var newPlayerImg: Bitmap = defaultImg(this)


            val newPlayer = Player(fName, lName, num.toInt(), teamID, position, bHand, height, weight, birthYear.toInt())


            Toast.makeText(this, "Player added.", Toast.LENGTH_SHORT).show()
            DBManager.getDB(this).playerDao().insert(newPlayer)

            super.finish()
        }
    }
}

