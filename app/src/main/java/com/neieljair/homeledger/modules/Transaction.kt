package com.neieljair.homeledger.modules

import android.provider.Settings.Global.getString
import java.math.BigDecimal
import java.util.*

data class Transaction (
    val from: User,
    val to: User,
    var amount: BigDecimal,
    val description: String = "",
    val date: Date = Date(),
) {
    init {
        require(from != to) {
            "participants must be different"
        }
    }

    override fun toString(): String {
        return "$from ${if (amount.signum() >= 0) "pays" else "owes"} \$$amount to $to"
    }

    operator fun contains(user: User): Boolean =
        user == from || user == to

    operator fun plus(other: Transaction): Transaction {
        require(from in other && to in other) {
            "participants must be present in both transactions"
        }
        return this.copy().add(
            if (from == other.from) other.amount else other.amount.negate()
        )
    }

    fun add(amount: BigDecimal): Transaction =
        this.apply { this.amount += amount }
}