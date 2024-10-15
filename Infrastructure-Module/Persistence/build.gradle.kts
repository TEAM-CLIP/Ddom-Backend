apply {
    plugin(Plugins.KOTLIN_JPA.id)
}

dependencies {
    implementation(Dependencies.Spring.DATA_JPA)
    runtimeOnly(Dependencies.DATABASE.MYSQL)
    runtimeOnly(Dependencies.DATABASE.H2)

    implementation(Dependencies.FLYWAY.FLYWAY_CORE)
    implementation(Dependencies.FLYWAY.FLYWAY_MYSQL)

    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.APPLICATION_MODULE))
    implementation(project(Modules.COMMON_MODULE))
}
