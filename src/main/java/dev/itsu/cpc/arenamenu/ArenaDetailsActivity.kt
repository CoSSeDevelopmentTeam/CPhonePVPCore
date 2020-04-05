package dev.itsu.cpc.arenamenu

import dev.itsu.cpc.MainActivity
import dev.itsu.pvpcore.model.Arena
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ModalResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity

class ArenaDetailsActivity(manifest: ApplicationManifest, private val arena: Arena) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val modalResponse = response as ModalResponse
        if (modalResponse.isButton1Clicked) ArenaMenuActivity(manifest).start(bundle)
        else MainActivity(manifest).start(bundle)
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("ad_title")
        this.content = """
            §l${arena.name}§r
            ${arena.description}
            
            ${bundle.getString("ad_id")}${arena.id}
            ${bundle.getString("ad_author")}${arena.owner}
            ${bundle.getString("ad_loc")}${arena.world}(${arena.x}, ${arena.y}, ${arena.z})
        """.trimIndent()
    }

}