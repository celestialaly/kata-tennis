package org.example.game.domain

class Player(private val name: String) {
    private val betterIndoor: Boolean = true;

    override fun toString(): String {
        return this.name
    }
}