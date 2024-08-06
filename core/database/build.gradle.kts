plugins {
    libs.plugins.yadino.run {
        alias(android.library)
        alias(android.hilt)
        alias(android.room.base)
    }
}

android {
    namespace = "com.rahim.yadino.core.database"
}
dependencies{
    implementation(project(":data:dateTime-local"))
    implementation(project(":data:routine-local"))
    implementation(project(":data:note-local"))
}