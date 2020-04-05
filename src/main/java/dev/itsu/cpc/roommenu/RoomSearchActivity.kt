package dev.itsu.cpc.roommenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.RoomManagementAPI
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.CustomResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity
import net.comorevi.cphone.cphone.widget.activity.original.MessageActivity
import net.comorevi.cphone.cphone.widget.element.Input

class RoomSearchActivity(manifest: ApplicationManifest) : CustomActivity(manifest) {

    private lateinit var bundle: Bundle
    override fun onStop(response: Response): ReturnType {
        val customResponse = response as CustomResponse
        val id = customResponse.result[0].toString().toIntOrNull()

        if (id != null) {
            val room = RoomManagementAPI.getInstance().getRoomById(id)
            if (room != null) {
                RoomDetailsActivity(manifest, room).start(bundle)
                return ReturnType.TYPE_CONTINUE
            }
        }

        MessageActivity(
                manifest,
                bundle.getString("rs_notfound"),
                bundle.getString("back_to_home"),
                bundle.getString("back_to_home"),
                MainActivity(manifest),
                MainActivity(manifest)
        ).start(bundle)

        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("rs_title")
        this.addFormElement(Input(bundle.getString("rs_des")))
    }

}