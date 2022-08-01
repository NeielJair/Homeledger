package com.neieljair.homeledger.modules

import android.provider.Settings.Global.getString
import java.math.BigDecimal
import java.util.*

class Transaction (
    val from: User,
    val to: User,
    var amount: BigDecimal,
    val description: String,
    val date: Date,
) {
    override fun toString(): String {
        return "$from ${if (amount.signum() >= 0) "pays" else "owes"} \$$amount to $to"
    }

    operator fun plus(other: Transaction) {
        if (from !in other || to !in other) {
            throw IllegalArgumentException("participants must be equal")
        }
        amount += if (from == other.from) other.amount else other.amount.negate()
    }

    operator fun contains(user: User): Boolean {
        return user == from || user == to
    }

    fun add(amount: BigDecimal) {
        this.amount += amount
    }
}