package dev.itsu.cpc.arenamenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.ArenaManagementAPI
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class ArenaMenuActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        when (listResponse.buttonIndex) {
            0 -> CreateArenaActivity(manifest).start(bundle)
            1 -> ArenaListActivity(manifest, ArenaManagementAPI.getInstance().getArenasByOwner(listResponse.player.name)).start(bundle)
            2 -> ArenaListActivity(manifest, ArenaManagementAPI.getInstance().arenas).start(bundle)
            else -> MainActivity(manifest).start(bundle)
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("am_title")
        addButton(Button(bundle.getString("am_create")))
        addButton(Button(bundle.getString("am_mine")))
        addButton((Button(bundle.getString("am_arenas"))))
    }
}