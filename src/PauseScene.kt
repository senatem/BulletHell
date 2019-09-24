package com.fulmirex.game.bullethell.scene

import com.fulmirex.game.engine.Game
import com.fulmirex.game.engine.Input
import com.fulmirex.game.engine.Scene
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyEvent

class PauseScene(game: Game) : Scene(game) {
    private val secondaryFont = Font("Default", Font.PLAIN, 20)
    private var choice = 0
    override fun update(nanosecondsPassed: Long) {
        game.input.consumeEvents().forEach {
            if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_ENTER) {
                when(choice){
                    0 -> game.scene = GameScene(game)
                    1 -> game.finish()
                }
            }
            else if(it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_UP) choice = 0
            else if (it is Input.Event.KeyPressed && it.data.keyCode == KeyEvent.VK_DOWN) choice = 1
        }
    }

    override fun draw(graphics: Graphics2D) {
        graphics.apply {
            color = Color(0, 0, 0, 100)
            fillRect(0, 0, game.width, game.height)

            font = secondaryFont
            color = Color.gray
            val con = "Continue"
            drawString(
                con,
                game.screenSize.width / 2 - fontMetrics.stringWidth(con) / 2,
                game.screenSize.height / 2 + 150
            )

            val quit = "Quit"
            drawString(
                quit,
                game.screenSize.width / 2 - fontMetrics.stringWidth(quit) / 2,
                game.screenSize.height / 2 + 200
            )

            fillOval(
                game.screenSize.width / 2 - fontMetrics.stringWidth(con) / 2 - 30,
                game.screenSize.height / 2 + 135 + 50 * choice,
                20, 20
            )
        }
    }

}
