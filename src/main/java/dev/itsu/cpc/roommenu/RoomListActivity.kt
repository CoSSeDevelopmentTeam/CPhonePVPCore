package dev.itsu.cpc.roommenu

import cn.nukkit.Player
import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.PVPCoreAPI
import dev.itsu.pvpcore.game.GameState
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class RoomListActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        if (listResponse.buttonIndex == ListResponse.NOT_SELECTED) {
            MainActivity(manifest).start(bundle)
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("title")
        this.content = bundle.getString("rl_des")

        PVPCoreAPI.Factory.getInstance().rooms
                .filter { !it.isPrivateRoom }
                .shuffled()
                .sortedBy { it.state == GameState.STATE_WAITING }
                .forEach {
                    val status = when (it.state) {
                        GameState.STATE_WAITING -> bundle.getString("rl_joinable")
                        GameState.STATE_PREPARING -> bundle.getString("rl_preparing")
                        GameState.STATE_FIGHTING -> bundle.getString("rl_playing")
                        GameState.STATE_FINISHED -> bundle.getString("rl_finished")
                    }

                    addButton(object : Button("${it.name} [${it.joiners.size}/${it.maxCount}\n${status}]") {
                        override fun onClick(player: Player) {
                            RoomDetailsActivity(manifest, it).start(bundle)
                        }
                    })
                }
    }

}