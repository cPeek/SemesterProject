package net.cpeek.semesterproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.line_editor.*
import net.cpeek.semesterproject.data.Line
import net.cpeek.semesterproject.data.Player
import net.cpeek.semesterproject.db.DBManager

class LinesActivity() : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var teamID: Int = -2

    lateinit var ld:Player
    lateinit var rd:Player
    lateinit var c:Player
    lateinit var lw:Player
    lateinit var rw:Player

    var selectedLine: Line? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.line_editor)

        val sharedPrefs = getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)
        teamID = sharedPrefs.getInt("team_id", -1)


        Log.println(Log.DEBUG,"hdb", "onCreate Team ID $teamID")
        val players: Array<Player> = DBManager.getDB(this).teamDao().getAllPlayersOnTeam(teamID)

        val adapter: ArrayAdapter<Player> = ArrayAdapter(this, android.R.layout.simple_spinner_item, players)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_ld.adapter = adapter
        sp_ld.onItemSelectedListener = this

        sp_rd.adapter = adapter
        sp_rd.onItemSelectedListener = this

        sp_c.adapter = adapter
        sp_c.onItemSelectedListener = this

        sp_lw.adapter = adapter
        sp_lw.onItemSelectedListener = this

        sp_rw.adapter = adapter
        sp_rw.onItemSelectedListener = this


        var lines = DBManager.getDB(this).lineDao().getLinesOfTeam(teamID)
        val linesAdapter: ArrayAdapter<Line> = ArrayAdapter(this, android.R.layout.simple_spinner_item, lines)
        linesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_lines.adapter = linesAdapter
        sp_lines.onItemSelectedListener = this

        load_Button.setOnClickListener {
            if(selectedLine != null){

                val db = DBManager.getDB(this)
                val mRD = db.playerDao().getPlayerByID(selectedLine!!.rd)
                val mLD = db.playerDao().getPlayerByID(selectedLine!!.ld)
                val mC = db.playerDao().getPlayerByID(selectedLine!!.c)
                val mRW = db.playerDao().getPlayerByID(selectedLine!!.rw)
                val mLW = db.playerDao().getPlayerByID(selectedLine!!.lw)

                sp_rd.setSelection(adapter.getPosition(mRD))
                sp_ld.setSelection(adapter.getPosition(mLD))
                sp_c.setSelection(adapter.getPosition(mC))
                sp_rw.setSelection(adapter.getPosition(mRW))
                sp_lw.setSelection(adapter.getPosition(mLW))
            }
        }
        save_Button.setOnClickListener {
            val lineName = line_name.text
            val newLine: Line = Line(lineName.toString(), teamID, ld.id, rd.id, c.id, lw.id, rw.id)
            DBManager.getDB(this).lineDao().insert(newLine)
            linesAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemSelected(spinner: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        var spinnerView = findViewById<Spinner>(spinner!!.id)

        when(spinner.id) {
            R.id.sp_rd -> rd = spinnerView.selectedItem as Player
            R.id.sp_ld -> ld = spinnerView.selectedItem as Player
            R.id.sp_c -> c = spinnerView.selectedItem as Player
            R.id.sp_rw -> rw = spinnerView.selectedItem as Player
            R.id.sp_lw -> lw = spinnerView.selectedItem as Player
            R.id.sp_lines -> selectedLine = spinnerView.selectedItem as Line
        }
        Log.println(Log.DEBUG, "hdb", "spinner listener")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}