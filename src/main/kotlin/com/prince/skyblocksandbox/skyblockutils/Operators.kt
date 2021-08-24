package com.prince.skyblocksandbox.skyblockutils

import java.math.BigDecimal
import java.math.BigInteger

operator fun BigInteger.div(i: Int): BigInteger {
    return this/i.toBigInteger()
}

operator fun Int.plus(bint: BigInteger): BigInteger {
    return this.toBigInteger()+bint
}

operator fun BigInteger.div(i: Double): BigDecimal {
    return this / i.toBigDecimal()
}

private operator fun BigInteger.div(i: BigDecimal): BigDecimal {
    return (this.toDouble()/i.toDouble()).toBigDecimal()
}
