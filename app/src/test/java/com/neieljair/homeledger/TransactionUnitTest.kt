package com.neieljair.homeledger

import com.neieljair.homeledger.modules.Transaction
import com.neieljair.homeledger.modules.User
import org.junit.Test
import org.junit.Assert.*
import java.lang.IllegalArgumentException
import java.math.BigDecimal

class TransactionUnitTest {
    private val user1 = User("user1")
    private val user2 = User("user2")
    private val user3 = User("user3")

    @Test
    fun differentUsers() {
        assertThrows(IllegalArgumentException::class.java) {
            Transaction(user1, user1, BigDecimal.ZERO)
        }
    }

    @Test
    fun addingTogether() {
        val t12 = Transaction(user1, user2, BigDecimal.ONE)
        val t21 = Transaction(user2, user1, BigDecimal.ONE)
        val t13 = Transaction(user1, user3, BigDecimal.ONE)

        assertTrue("Same participants", (t12 + t12).amount.compareTo(BigDecimal("2")) == 0)
        assertTrue("Opposite participants", (t12 + t21).amount.compareTo(BigDecimal.ZERO) == 0)
        assertThrows(IllegalArgumentException::class.java) {
            t12 + t13
        }
    }

    @Test
    fun amountAddition() {
        val t = Transaction(user1, user2, BigDecimal("50"))

        t.add(BigDecimal("30.5"))
        assertTrue("Positive addition", t.amount.compareTo(BigDecimal("80.5")) == 0)

        t.add(BigDecimal.ZERO)
        assertTrue("Zero addition", t.amount.compareTo(BigDecimal("80.5")) == 0)

        t.add(BigDecimal("-30.5"))
        assertTrue("Negative addition", t.amount.compareTo(BigDecimal("50")) == 0)
    }
}