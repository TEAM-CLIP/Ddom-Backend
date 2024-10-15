dependencies {
    implementation(Dependencies.Jwt.JWT_API)
    runtimeOnly(Dependencies.Jwt.JWT_JACKSON)
    runtimeOnly(Dependencies.Jwt.JWT_IMPL)

    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.COMMON_MODULE))
    implementation(project(Modules.APPLICATION_MODULE))
}