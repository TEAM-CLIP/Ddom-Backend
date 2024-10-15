dependencies{
    implementation(Dependencies.Spring.WEBFLUX)
    testImplementation(Dependencies.Spring.REACTOR_TEST)
    testFixturesImplementation(Dependencies.MockServer.MOCK_SERVER)

    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.APPLICATION_MODULE))
    implementation(project(Modules.COMMON_MODULE))
    implementation(project(Modules.INFRASTRUCTURE_SECURITY_MODULE))
}