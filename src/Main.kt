package com.fulmirex.game.engine

import com.fulmirex.game.bullethell.scene.MainMenuScene
import java.awt.Dimension

fun main(args: Array<String>) {
    val screenSize = Dimension(800, 600)
    val game = GameFactory.create(screenSize)
    game.loadGame()
    game.scene = MainMenuScene(game)
    game.play()
}