plugins {
    libs.plugins.yadino.run {
        alias(android.library)
        alias(android.hilt)
    }
}

android {
    namespace = "com.rahim.yadino.domin.dateTime"
}
dependencies{
    implementation(project(":core:base"))
}