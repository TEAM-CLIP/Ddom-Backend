dependencies {
    implementation(Dependencies.Spring.WEB)
    implementation(Dependencies.TemplateEngine.THYMELEAF)
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
//    implementation("org.springframework.boot:spring-boot-devtools")

    implementation(project(Modules.APPLICATION_MODULE))
    implementation(project(Modules.COMMON_MODULE))
    implementation(project(Modules.INFRASTRUCTURE_PERSISTENCE_MODULE))
    implementation(project(Modules.INFRASTRUCTURE_CLIENT_MODULE))
    implementation(project(Modules.INFRASTRUCTURE_SECURITY_MODULE))

}