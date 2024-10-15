package com.clip.common.security

import java.security.Principal

interface Authentication<T>: Principal {

    fun getDetails(): T
}