package com.fulmirex.game.bullethell.model

data class Player(var x: Int, var y: Int) {
    fun move(direction: Direction, worldHeight: Int) {
        when {
            direction == Direction.UP && y < worldHeight -> y += 5
            direction == Direction.DOWN && y > 5 -> y -= 5
        }
    }
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}