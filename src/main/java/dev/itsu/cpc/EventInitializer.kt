package dev.itsu.cpc

import net.comorevi.cphone.cphone.application.Initializer
import net.comorevi.cphone.presenter.SharingData

class EventInitializer : Initializer() {
    override fun initialize() {
        SharingData.server.pluginManager.registerEvents(Ev(), SharingData.pluginInstance)
    }
}