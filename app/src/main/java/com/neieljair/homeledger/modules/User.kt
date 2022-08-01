package com.neieljair.homeledger.modules

data class User (
    val username: String
) {
    override fun toString(): String {
        return this.username
    }
}