object Modules {

    const val ADMIN_MODULE = ":BootStrap-Module:Admin"
    const val API_MODULE = ":BootStrap-Module:Api"
    const val BOOTSTRAP_BASE_MODULE = ":BootStrap-Module"

    val BOOTSTRAP_MODULES = listOf(ADMIN_MODULE, API_MODULE)

    const val APPLICATION_MODULE = ":Application-Module"
    const val DOMAIN_MODULE = ":Domain-Module"
    const val COMMON_MODULE = ":Common-Module"
    const val INFRASTRUCTURE_CLIENT_MODULE = ":Infrastructure-Module:Client"
    const val INFRASTRUCTURE_SECURITY_MODULE = ":Infrastructure-Module:Security"
    const val INFRASTRUCTURE_PERSISTENCE_MODULE = ":Infrastructure-Module:Persistence"
    const val INFRASTRUCTURE_UUID_MODULE = ":Infrastructure-Module:UUID"
}