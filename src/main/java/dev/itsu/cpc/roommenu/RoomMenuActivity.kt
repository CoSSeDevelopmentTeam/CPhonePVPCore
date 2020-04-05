package dev.itsu.cpc.roommenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.RoomManagementAPI
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class RoomMenuActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        when (listResponse.buttonIndex) {
            0 -> RoomListActivity(manifest).start(bundle)
            1 -> RoomSearchActivity(manifest).start(bundle)
            2 -> randomActivity()
            3 -> RoomDetailsActivity(manifest, RoomManagementAPI.getInstance().getEntryingRoom(response.player.name)).start(bundle)
            else -> MainActivity(manifest).start(bundle)
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("rm_title")
        this.addButton(Button(bundle.getString("rm_list")))
        this.addButton(Button(bundle.getString("rm_search")))
        this.addButton(Button(bundle.getString("rm_random")))
        this.addButton(Button(bundle.getString("rm_leave")))
    }

    private fun randomActivity() {
        val room = RoomManagementAPI.getInstance().rooms
                .filter { !it.isPrivateRoom }
                .random()
        RoomDetailsActivity(manifest, room).start(bundle)
    }

}