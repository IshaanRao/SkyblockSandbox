package com.prince.skyblocksandbox.skyblockutils

import java.math.BigInteger

private operator fun BigInteger.div(i: Int): BigInteger {
    return this/i.toBigInteger()
}

operator fun Int.plus(bint: BigInteger): BigInteger {
    return this.toBigInteger()+bint
}