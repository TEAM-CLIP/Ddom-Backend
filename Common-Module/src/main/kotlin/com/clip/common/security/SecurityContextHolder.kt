package com.clip.common.security

class SecurityContextHolder {
    companion object {
        private val contextHolder = ThreadLocal<SecurityContext<*, *>>()

        fun getContext(): SecurityContext<*, *> {
            return contextHolder.get()
        }

        fun setContext(context: SecurityContext<*, *>) {
            contextHolder.set(context)
        }

        fun clearContext() {
            contextHolder.remove()
        }
    }
}