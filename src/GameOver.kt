package com.fulmirex.game.bullethell.scene

import com.fulmirex.game.engine.*
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyEvent

class GameOver(game: Game) : Scene(game) {
    private val primaryFont = Font("Default", Font.BOLD, 30)
    private val secondaryFont = Font("Default", Font.PLAIN, 20)
    override fun update(nanosecondsPassed: Long) {
        game.input.consumeEvents().forEach {
            if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_ENTER) {
                game.scene = GameScene(game)
            } else if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_ESCAPE) {
                game.scene = MainMenuScene(game)
            }
        }
    }

    override fun draw(graphics: Graphics2D) {
        graphics.apply {
            color = Color.black
            fillRect(0, 0, game.screenSize.width, game.screenSize.height)

            font = primaryFont
            color = Color.white
            val score = "SCORE : ${game.finalscore}"

            drawString(
                score,
                game.screenSize.width / 2 - fontMetrics.stringWidth(score) / 2,
                game.screenSize.height / 2 - 50
            )

            font = secondaryFont
            color = Color.gray
            var message = "Press Enter to start a new game"

            drawString(
                message,
                game.screenSize.width / 2 - fontMetrics.stringWidth(message) / 2,
                game.screenSize.height / 2 + 50
            )
            message = "Press Esc to return to the main menu"

            drawString(
                message,
                game.screenSize.width / 2 - fontMetrics.stringWidth(message) / 2,
                game.screenSize.height / 2 + 150
            )
        }
    }
}