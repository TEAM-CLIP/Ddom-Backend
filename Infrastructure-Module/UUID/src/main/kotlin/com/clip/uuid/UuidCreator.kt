package com.clip.uuid

import com.fasterxml.uuid.Generators
import java.util.*

object UuidCreator {

    fun create(): UUID {
        return Generators.timeBasedEpochGenerator().generate()
    }

}