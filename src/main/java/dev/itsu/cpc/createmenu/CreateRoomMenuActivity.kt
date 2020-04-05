package dev.itsu.cpc.createmenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.RoomManagementAPI
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.activity.original.MessageActivity
import net.comorevi.cphone.cphone.widget.element.Button

class CreateRoomMenuActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        when (listResponse.buttonIndex) {
            0 -> {
                if (!RoomManagementAPI.getInstance().isEntrying(response.player.name)) {
                    CreateRoomActivity(manifest).start(bundle)
                } else {
                    MessageActivity(
                            manifest,
                            bundle.getString("crm_already_created"),
                            bundle.getString("back_to_home"),
                            bundle.getString("back_to_home"),
                            MainActivity(manifest),
                            MainActivity(manifest)
                    ).start(bundle)
                }
            }
            else -> MainActivity(manifest).start(bundle)
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("crm_title")
        this.addButton(Button(bundle.getString("crm_create")))
        this.addButton(Button(bundle.getString("crm_delete")))
        this.addButton(Button(bundle.getString("crm_myroom")))
    }
}