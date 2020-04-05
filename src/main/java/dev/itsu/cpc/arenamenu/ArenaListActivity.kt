package dev.itsu.cpc.arenamenu

import cn.nukkit.Player
import dev.itsu.pvpcore.api.ArenaManagementAPI
import dev.itsu.pvpcore.model.Arena
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class ArenaListActivity(manifest: ApplicationManifest, private val list: List<Arena>) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        if (listResponse.buttonIndex == ListResponse.NOT_SELECTED) {
            ArenaMenuActivity(manifest).start(bundle)
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("ca_title")

        list.forEach {
            addButton(object : Button(it.name) {
                override fun onClick(player: Player) {
                    ArenaDetailsActivity(manifest, it).start(bundle)
                }
            })
        }
    }
}