package dev.itsu.cpc

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import net.comorevi.cphone.cphone.event.CPhoneOpenEvent

class Ev : Listener {

    @EventHandler
    fun onOpen(e: CPhoneOpenEvent) {
        println("Fired!")
        e.setCancelled()
    }
}