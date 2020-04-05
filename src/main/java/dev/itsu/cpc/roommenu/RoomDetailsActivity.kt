package dev.itsu.cpc.roommenu

import cn.nukkit.utils.TextFormat
import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.ArenaManagementAPI
import dev.itsu.pvpcore.api.RoomManagementAPI
import dev.itsu.pvpcore.game.GameState
import dev.itsu.pvpcore.model.MatchRoom
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ModalResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity
import net.comorevi.cphone.cphone.widget.activity.original.MessageActivity

class RoomDetailsActivity(manifest: ApplicationManifest, private val room: MatchRoom) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val modalResponse = response as ModalResponse
        var text: String

        if (!response.isButton1Clicked) {
            RoomListActivity(manifest).start(bundle)
            return ReturnType.TYPE_CONTINUE
        }

        val gameAPI = RoomManagementAPI.getInstance()
        text =
                if (room.state == GameState.STATE_WAITING) {
                    if (room.owner == bundle.cPhone.player.name) {
                        RoomListActivity(manifest).start(bundle)
                        return ReturnType.TYPE_CONTINUE

                    } else if (gameAPI.isEntrying(modalResponse.player.name) && !room.joiners.contains(response.player.name)) {
                        gameAPI.cancelEntry(gameAPI.getEntryingRoom(modalResponse.player.name).id, modalResponse.player.name)
                        bundle.getString("rd_left_and_joined")

                    } else if (room.joiners.contains(response.player.name)) {
                        gameAPI.cancelEntry(room.id, modalResponse.player.name)
                        bundle.getString("rd_left")

                    } else {
                        gameAPI.entry(room.id, modalResponse.player.name)
                        bundle.getString("rd_joined")
                    }

                } else {
                    bundle.getString("rd_cannot_join_des")
                }

        MessageActivity(
                manifest,
                text,
                bundle.getString("rd_back"),
                bundle.getString("back_to_home"),
                RoomListActivity(manifest),
                MainActivity(manifest)
        ).start(bundle)

        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("rd_title")
        this.button2Text = bundle.getString("rd_back")
        this.button1Text =
                if (room.owner == bundle.cPhone.player.name) {
                    bundle.getString("rd_back")

                } else if (RoomManagementAPI.getInstance().isEntrying(bundle.cPhone.player.name) && !room.joiners.contains(bundle.cPhone.player.name)) {
                    bundle.getString("rd_left_and_join")

                } else if (room.joiners.contains(bundle.cPhone.player.name)) {
                    bundle.getString("rd_leave")

                } else {
                    when (room.state) {
                        GameState.STATE_WAITING -> bundle.getString("rd_join")
                        else -> bundle.getString("rd_cannot_join")
                    }
                }

        var players = ""
        room.joiners.forEach {
            players += "${it}, "
        }
        players.removeSuffix(", ")

        this.content =
                """
                    ${TextFormat.BOLD}${room.name}
                    ${TextFormat.RESET}${room.description}
                    
                    ${bundle.getString("rd_id")}${room.id}
                    ${bundle.getString("rd_arena")}${ArenaManagementAPI.getInstance().getArenaById(room.arenaId).name}
                    ${bundle.getString("rd_count")}${room.joiners.size}/${room.maxCount}
                    ${bundle.getString("rd_players")}${players}
                """.trimIndent()
    }

}