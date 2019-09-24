package com.fulmirex.game.bullethell.scene

import com.fulmirex.game.engine.*
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyEvent

class ScoreScene(game: Game) : Scene(game) {

    private val primaryFont = Font("Default", Font.BOLD, 30)
    private val secondaryFont = Font("Default", Font.PLAIN, 20)

    override fun draw(graphics: Graphics2D) {
        graphics.apply {
            color = Color.black
            fillRect(0, 0, game.screenSize.width, game.screenSize.height)

            font = primaryFont
            color = Color.white
            val name = "High Scores"

            drawString(
                name,
                game.screenSize.width / 2 - fontMetrics.stringWidth(name) / 2,
                game.screenSize.height / 2 - 100
            )

            font = secondaryFont
            color = Color.gray

            val message = "Press Esc to return to the main menu"
            drawString(
                message,
                game.screenSize.width / 2 - fontMetrics.stringWidth(message) / 2,
                game.screenSize.height / 2 - 50
            )

            for(i in 0..4) {
                drawString(
                    game.highScores[i].toString(),
                    game.screenSize.width / 2 - fontMetrics.stringWidth(game.highScores[i].toString()) / 2,
                    game.screenSize.height / 2 + i * 50
                )
            }
        }
    }

    override fun update(nanosecondsPassed: Long) {
        game.input.consumeEvents().forEach {
            if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_ESCAPE) {
                game.scene = MainMenuScene(game)
            }
        }
    }
}