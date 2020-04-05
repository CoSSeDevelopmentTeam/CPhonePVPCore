package dev.itsu.cpc

import dev.itsu.cpc.arenamenu.ArenaMenuActivity
import dev.itsu.cpc.createmenu.CreateRoomMenuActivity
import dev.itsu.cpc.roommenu.RoomMenuActivity
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class MainActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        when (listResponse.buttonIndex) {
            0 -> RoomMenuActivity(manifest).start(bundle)
            1 -> CreateRoomMenuActivity(manifest).start(bundle)
            2 -> ArenaMenuActivity(manifest).start(bundle)
            // TODO Start an activity that explains how to play PVP | 3 -> ExplainActivity(manifest).start(bundle)
            else -> return ReturnType.TYPE_END
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("title")
        this.addButton(Button(bundle.getString("m_join")))
        this.addButton(Button(bundle.getString("m_create")))
        this.addButton(Button(bundle.getString("m_arena")))
        this.addButton(Button(bundle.getString("m_howto")))
    }

}