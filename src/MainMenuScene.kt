package com.fulmirex.game.bullethell.scene

import com.fulmirex.game.engine.*
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyEvent

class MainMenuScene(game: Game) : Scene(game) {
    private val primaryFont = Font("Default", Font.BOLD, 30)
    private val secondaryFont = Font("Default", Font.PLAIN, 20)
    private var choice = 0

    override fun update(nanosecondsPassed: Long) {
        game.input.consumeEvents().forEach {
            if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_ENTER) {
                when(choice){
                    0 -> game.scene = GameScene(game)
                    1 -> game.scene = ScoreScene(game)
                }
            }
            else if(it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_UP) choice = 0
            else if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_DOWN) choice = 1
        }
    }

    override fun draw(graphics: Graphics2D) {
        graphics.apply {
            color = Color.black
            fillRect(0, 0, game.screenSize.width, game.screenSize.height)

            font = primaryFont
            color = Color.white
            val name = "Bullet Hell"

            drawString(
                name,
                game.screenSize.width / 2 - fontMetrics.stringWidth(name) / 2,
                game.screenSize.height / 2 - 50
            )

            font = secondaryFont
            color = Color.gray
            val newGame = "New Game"
            drawString(
                newGame,
                game.screenSize.width / 2 - fontMetrics.stringWidth(newGame) / 2,
                game.screenSize.height / 2 + 150
            )

            val loadGame = "High Scores"
            drawString(
                loadGame,
                game.screenSize.width / 2 - fontMetrics.stringWidth(loadGame) / 2,
                game.screenSize.height / 2 + 200
            )

            fillOval(
                game.screenSize.width / 2 - fontMetrics.stringWidth(loadGame) / 2 - 30,
                game.screenSize.height / 2 + 135 + 50 * choice,
                20, 20
            )
        }
    }
}