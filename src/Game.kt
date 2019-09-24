package com.fulmirex.game.engine

import com.fulmirex.game.bullethell.model.Bullet
import com.fulmirex.game.bullethell.model.Player
import com.fulmirex.game.bullethell.scene.GameOver
import kotlinx.coroutines.*
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics2D
import java.io.File
import java.util.concurrent.TimeUnit

class Game(val screenSize: Dimension) : Canvas() {
    var scene: Scene? = null
    var points = 0
    var finalscore = 0
    val input = Input()
    var highScores = mutableListOf<Int>()
    var bullets = mutableListOf<Bullet>()
    var player = Player(3, 10)
    var bulletCount = 0
    val bulletTimer = Timer(TimeUnit.MILLISECONDS.toNanos(15))

    private var gameLoop: Job? = null

    init {
        size = screenSize
        addKeyListener(input)
    }

    fun finish(){
        addHighScore(points)
        finalscore = points
        points = 0
        saveGame()
        bulletCount = 0
        bulletTimer.reset()
        bullets.clear()
        player = Player(3, 10)
        scene = GameOver(this)
    }
    fun play() {
        if (gameLoop != null) return

        gameLoop = GlobalScope.launch {
            var previousIterationTime = System.nanoTime()

            while (isActive) {
                val timePassed = System.nanoTime() - previousIterationTime
                previousIterationTime = System.nanoTime()
                scene?.updateAndDraw(timePassed, bufferStrategy.drawGraphics as Graphics2D)
                bufferStrategy.show()
            }
        }
    }

    fun pause() = runBlocking {
        gameLoop?.cancel()
        gameLoop?.join()
        gameLoop = null
    }

    fun addHighScore(score : Int){
        highScores.apply{
            add(score)
            sortDescending()
        }
    }

    fun loadGame(){
        if(File("scoreList").exists()) {
            val lines = File("scoreList").readLines()
            var i = 0
            for(i in 0..4) {
                addHighScore(lines[i].toInt())
            }
        }
        else{
            for(i in 1..5){
                addHighScore(i * 100)
            }
        }
    }

    fun saveGame(){
        var file = File("scoreList")
        file.delete()
        for(score in highScores){
            file.appendText("$score\n")
        }
    }
}