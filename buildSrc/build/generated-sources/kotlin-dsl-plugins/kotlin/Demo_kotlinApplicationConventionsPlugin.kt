/**
 * Precompiled [demo.kotlin-application-conventions.gradle.kts][Demo_kotlin_application_conventions_gradle] script plugin.
 *
 * @see Demo_kotlin_application_conventions_gradle
 */
public
class Demo_kotlinApplicationConventionsPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Demo_kotlin_application_conventions_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
