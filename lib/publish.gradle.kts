//plugins {
//    id("maven-publish")
//}
//
//val groupId = project.property("GROUP_ID") ?: "com.github.joaoeudes7"
//val artifactId = project.property("ARTIFACT_ID") ?: "paginationfast"
//val versionName = project.property("VERSION_NAME") ?: "1.0.0"
//
//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("release") {
//                from(components.release)
//                groupId.set(groupId)
//                artifactId.set(artifactId)
//                version.set(versionName)
//            }
//        }
//        repositories {
//            mavenLocal()  // Consider using a remote repository for actual publishing
//        }
//    }
//}
//
//tasks.register("publishLocal") {
//    dependsOn(tasks.publishToMavenLocal)
//}