package com.fulmirex.game.engine

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Input : KeyListener {
    private val events = mutableListOf<Event>()

    override fun keyTyped(e: KeyEvent) {
        // We're not interested in this kind of events
    }

    override fun keyPressed(e: KeyEvent) {
        synchronized(this) {
            events += Event.KeyPressed(e)
        }
    }

    override fun keyReleased(e: KeyEvent) {
        synchronized(this) {
            events += Event.KeyReleased(e)
        }
    }

    fun consumeEvents(): List<Event> {
        synchronized(this) {
            val consumedEvents = events.toList()
            events.clear()
            return consumedEvents
        }
    }

    sealed class Event {
        data class KeyPressed(val data: KeyEvent) : Event()
        data class KeyReleased(val data: KeyEvent) : Event()
    }
}