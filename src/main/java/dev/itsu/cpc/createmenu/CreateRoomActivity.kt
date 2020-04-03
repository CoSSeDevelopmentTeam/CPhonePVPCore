package dev.itsu.cpc.createmenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.api.PVPCoreAPI
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.CustomResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity
import net.comorevi.cphone.cphone.widget.activity.original.MessageActivity
import net.comorevi.cphone.cphone.widget.element.Dropdown
import net.comorevi.cphone.cphone.widget.element.Input
import net.comorevi.cphone.cphone.widget.element.Slider
import net.comorevi.cphone.cphone.widget.element.Toggle

class CreateRoomActivity(manifest: ApplicationManifest) : CustomActivity(manifest) {

    private lateinit var bundle: Bundle
    override fun onStop(response: Response): ReturnType {
        val customResponse = response as CustomResponse
        var error = false
        var text = ""

        val name = customResponse.result[0].toString()
        val description = customResponse.result[1].toString()
        val arena = customResponse.result[2].toString()
        val max = customResponse.result[3].toString().toFloat().toInt()
        val min = customResponse.result[4].toString().toFloat().toInt()
        val private = customResponse.result[5].toString().toBoolean()

        if (name.isEmpty() || name.length > 20) {
            text = bundle.getString("cr_ex_count_name")
            error = true
        }

        if (description.isNotEmpty() && description.length > 100) {
            text = bundle.getString("cr_ex_count_des")
            error = true
        }

        if (min > max) {
            text = bundle.getString("cr_ex_count")
            error = true
        }

        if (!error) {
            text = bundle.getString("cr_created") +
                    PVPCoreAPI.Factory.getInstance().createRoom(
                            response.player.name,
                            name,
                            description,
                            max,
                            min,
                            private,
                            0
                    )
        }

        MessageActivity(
                manifest,
                text,
                bundle.getString("back_to_home"), 
                bundle.getString("back_to_home"),
                MainActivity(manifest),
                MainActivity(manifest)
        ).start(bundle)

        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("crm_create")
        this.addFormElement(Input(bundle.getString("cr_name")))
        this.addFormElement(Input(bundle.getString("cr_des")))
        this.addFormElement(Dropdown(bundle.getString("cr_arena"), listOf("test")))
        this.addFormElement(Slider(bundle.getString("cr_max"), 2.0F, 6.0F, 1.0F, 2.0F))
        this.addFormElement(Slider(bundle.getString("cr_min"), 2.0F, 6.0F, 1.0F, 2.0F))
        this.addFormElement(Toggle(bundle.getString("cr_private"), false))
    }

}