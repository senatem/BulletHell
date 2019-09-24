package com.fulmirex.game.engine

import java.awt.Graphics2D

abstract class Scene(val game: Game) {
    fun updateAndDraw(nanosecondsPassed: Long, graphics: Graphics2D) {
        update(nanosecondsPassed)
        draw(graphics)
    }

    protected abstract fun update(nanosecondsPassed: Long)

    protected abstract fun draw(graphics: Graphics2D)
}