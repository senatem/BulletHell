package com.fulmirex.game.bullethell.scene

import com.fulmirex.game.bullethell.model.*
import com.fulmirex.game.engine.*
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.util.concurrent.TimeUnit

class GameScene(game: Game) : Scene(game) {
    override fun draw(graphics: Graphics2D) {
        graphics.apply {
            color = Color.black
            fillRect(0, 0, game.screenSize.width, game.screenSize.height)

            for (bullet in game.bullets) {
                drawBullet(this, bullet)
            }
            drawPlayer(this)
        }
    }

    private fun createBullet(): Bullet {
        var y = (1 + (Math.random() * WORLD_HEIGHT)).toInt()

        while (!isCellEmpty(WORLD_WIDTH, y)) {
            y = when {
                y < WORLD_HEIGHT -> y + 1
                else -> 1
            }
        }
        return Bullet(WORLD_WIDTH, y)
    }

    private fun isCellEmpty(x: Int, y: Int): Boolean {
        for (bullet in game.bullets) {
            if (bullet.x == x && bullet.y == y) return false
        }
        return true
    }

    private fun drawBullet(graphics: Graphics2D, bullet: Bullet) {
        graphics.apply {
            color = Color.orange

            fillRect(
                bullet.x * CELL_SIZE - CELL_SIZE,
                game.screenSize.height - bullet.y * CELL_SIZE,
                CELL_SIZE * 2,
                CELL_SIZE * 2
            )
        }
    }

    private fun drawPlayer(graphics: Graphics2D) {
        graphics.apply {
            color = Color.white

            fillRect(
                game.player.x * CELL_SIZE - CELL_SIZE,
                game.screenSize.height - game.player.y * CELL_SIZE,
                CELL_SIZE * 5,
                CELL_SIZE * 5
            )
        }
    }

    override fun update(nanosecondsPassed: Long) {
        if (gameIsOver()) {
            game.finish()
            return
        }

        processInput()

        game.bulletTimer.update(nanosecondsPassed)

        if (game.bulletTimer.timeIsUp()) {
            game.points++
            for (bullet in game.bullets) {
                bullet.x--
            }
            game.bulletCount = (Math.random() * 2).toInt()
            for (i in 1..game.bulletCount) {
                game.bullets.add(createBullet())
            }
            game.bulletTimer.reset()
        }
    }

    fun gameIsOver(): Boolean {
        for(i in game.player.x-4 .. game.player.x)
            for(j in game.player.y-4 .. game.player.y)
                if(!isCellEmpty(i, j))
                    return true
        return false
    }
    private fun processInput() {
        for (event in game.input.consumeEvents()) {
            if (event is Input.Event.KeyPressed ){
                when (event.data.keyCode) {
                    KeyEvent.VK_UP -> game.player.move(Direction.UP, WORLD_HEIGHT)
                    KeyEvent.VK_DOWN -> game.player.move(Direction.DOWN, WORLD_HEIGHT)
                    KeyEvent.VK_ESCAPE -> game.scene = PauseScene(game)
                    //  KeyEvent.VK_RIGHT -> player.shoot()
                }
            }
        }
    }

    companion object {
        const val WORLD_WIDTH = 160
        const val WORLD_HEIGHT = 120
        const val CELL_SIZE = 5
    }
}