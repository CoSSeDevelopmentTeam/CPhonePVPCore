package dev.itsu.cpc.arenamenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.ArenaManagementAPI
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.CustomResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity
import net.comorevi.cphone.cphone.widget.activity.original.MessageActivity
import net.comorevi.cphone.cphone.widget.element.Input
import net.comorevi.cphone.cphone.widget.element.Label

class CreateArenaActivity(manifest: ApplicationManifest) : CustomActivity(manifest) {

    private lateinit var bundle: Bundle
    override fun onStop(response: Response): ReturnType {
        val customResponse = response as CustomResponse

        if (customResponse.result.size == 0) {
            ArenaMenuActivity(manifest).start(bundle)

        } else {
            var text = bundle.getString("ca_success")
            val name = customResponse.result[1].toString()
            val description = customResponse.result[2].toString()
            try {
                ArenaManagementAPI.getInstance().createArena(
                        name,
                        customResponse.player.name,
                        description,
                        customResponse.player.level.name,
                        customResponse.player.floorX,
                        customResponse.player.floorY,
                        customResponse.player.floorZ
                )
            } catch (e: IllegalArgumentException) {
                text = bundle.getString("ca_error")
            }
            MessageActivity(manifest, text, bundle.getString("back_to_home"), "", MainActivity(manifest)).start(bundle)
        }

        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        addFormElement(Label(bundle.getString("ca_des")))
        addFormElement(Input(bundle.getString("ca_name")))
        addFormElement(Input(bundle.getString("ca_ides")))
    }

}