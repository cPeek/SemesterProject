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
import kotlinx.android.synthetic.main.player_list.*
import net.cpeek.semesterproject.data.Player
import net.cpeek.semesterproject.data.Team
import net.cpeek.semesterproject.db.DBManager
import net.cpeek.semesterproject.db.HockeyDB
import kotlin.math.floor
import kotlin.math.roundToInt

// TODO: make the edit button do something

class PlayersActivity() : AppCompatActivity() {


    var teamID: Int = -2
    var team: Team? = null

    var players: Array<Player> = emptyArray()
    var db:HockeyDB? = null

    lateinit var playerAdapter: PlayersRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_list)

        db = DBManager.getDB(this)


        val sharedPrefs = this.getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)
        teamID = sharedPrefs.getInt("team_id", -1)
        Log.println(Log.DEBUG, "hdb", "onCreate Team ID: $teamID")

        team = db!!.teamDao().getTeamByID(teamID)

        players_teamName.setText(team?.teamName)

        players = db!!.teamDao().getAllPlayersOnTeam(teamID)

        playerAdapter = PlayersRecyclerAdapter(players, this)
        players_recycler.layoutManager = LinearLayoutManager(this)
        players_recycler.adapter = playerAdapter
        playerAdapter.update(players)

        players_addPlayer.setOnClickListener{
            val intent = Intent(this, AddPlayerActivity::class.java)

            intent.putExtra("team", teamID)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.println(Log.DEBUG, "hdb", "onResume Team ID: $teamID")
        players = db!!.teamDao().getAllPlayersOnTeam(teamID)
        playerAdapter.update(players)
    }
}
class PlayersRecyclerAdapter(private val players:Array<Player>, private val context: Context): RecyclerView.Adapter<PlayersRecyclerAdapter.ViewHolder>(){

    private lateinit var mPlayers: Array<Player>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.player_card, parent, false)
        return ViewHolder(view)
    }

    public fun update(players: Array<Player>){
        mPlayers = emptyArray()
        mPlayers = players
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val sharedPrefs = context.getSharedPreferences("cpeek_hdb_prefs", Context.MODE_PRIVATE)
        val metric = sharedPrefs.getBoolean("metric", true)

        val player = mPlayers[position]
        holder.playerName.text = player.firstName +" " + player.lastName
        //TODO: image support
        //holder.teamImg.setImageBitmap(player.img)
        var desc = "${player.position} | Shoots: "
        if(player.hand){
            desc += "R "
        } else {
            desc += "L "
        }


        if(metric) {
            desc += "| ${player.height}cm | ${player.weight}kg |"
        } else{
            val heightInches = player.height!!/2.54
            val feet: Double = floor(heightInches / 12)
            val inches: Double = floor(heightInches % 12)

            val weightLbs = (player.weight!!*2.205).roundToInt()

            desc += "| ${feet.roundToInt()}\' ${inches.roundToInt()}\" | ${weightLbs}lbs |"

        }
        desc += " ${player.birthday}"
        holder.playerDesc.text = desc
    }

    override fun getItemCount(): Int {
        return mPlayers.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val playerName: TextView
        val playerImg: ImageView
        val playerDesc: TextView

        init {
            playerName = itemView.findViewById(R.id.playercard_name)
            playerImg = itemView.findViewById(R.id.playercard_img)
            playerDesc = itemView.findViewById(R.id.playercard_desc)
        }
    }
}