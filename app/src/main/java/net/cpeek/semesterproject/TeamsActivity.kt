package net.cpeek.semesterproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.teams_editor.*
import net.cpeek.semesterproject.data.Team
import net.cpeek.semesterproject.db.DBManager
import net.cpeek.semesterproject.db.HockeyDB
import net.cpeek.semesterproject.db.byteArrayToBmp

// TODO: make the edit button do something

class TeamsActivity: AppCompatActivity() {


    var teams: Array<Team> = emptyArray()
    var db:HockeyDB? = null

    lateinit var teamsAdapter: TeamsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.teams_editor)

        db = DBManager.getDB(this)

        teams = db!!.teamDao().getAllTeams()

        teamsAdapter = TeamsRecyclerAdapter(teams, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = teamsAdapter

        teamsAdapter.update(teams)


        loadLine_button.setOnClickListener {
            var intent = Intent(this, AddTeamActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onResume() {

        // We are returning to this activity, presumably from the Add Team menu. Query the database
        // to get the new team in the dataset and notify the RecyclerView adapter
        super.onResume()
        teams = db!!.teamDao().getAllTeams()
        teamsAdapter.update(teams)
    }
}

class TeamsRecyclerAdapter(private val teams:Array<Team>, private val context: Context): RecyclerView.Adapter<TeamsRecyclerAdapter.ViewHolder>(){

    private var mTeams: Array<Team> = emptyArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.teamlist_edit_item, parent, false)
        return ViewHolder(view)
    }

    public fun update(teams: Array<Team>){
        mTeams = emptyArray()
        mTeams = teams
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = mTeams[position]
        holder.teamName.text = team.teamName
        holder.teamImg.setImageBitmap(byteArrayToBmp(team.img!!))
    }

    override fun getItemCount(): Int {
        return mTeams.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val teamName: TextView
        val teamImg: ImageView

        init {
            teamName = itemView.findViewById(R.id.playercard_name)
            teamImg = itemView.findViewById(R.id.playercard_img)
        }
    }
}