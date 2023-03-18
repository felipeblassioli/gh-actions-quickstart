:samples-dir: /home/tcagent1/agent/work/64493a816be20d5a/promote-projects/gradle/build/git-checkout/subprojects/docs/build/working/samples/install/building-kotlin-applications-multi-project
:gradle-version: 8.0.1

= Building Kotlin Applications with libraries Sample

[.download]
- link:zips/sample_building_kotlin_applications_multi_project-groovy-dsl.zip[icon:download[] Groovy DSL]
- link:zips/sample_building_kotlin_applications_multi_project-kotlin-dsl.zip[icon:download[] Kotlin DSL]

NOTE: You can open this sample inside an IDE using the https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start[IntelliJ native importer] or https://projects.eclipse.org/projects/tools.buildship[Eclipse Buildship].

To prepare your software project for growth, you can organize a Gradle project into multiple subprojects to modularize the software you are building.
In this guide, you'll learn how to structure such a project on the example of a Kotlin application.
However, the general concepts apply for any software you are building with Gradle.
You can follow the guide step-by-step to create a new project from scratch or download the complete sample project using the links above.

== What you’ll build

You'll build a Kotlin application that consists of an _application_ and multiple _library_ projects.

== What you’ll need

* A text editor or IDE - for example link:https://www.jetbrains.com/idea/download/[IntelliJ IDEA]
* A Java Development Kit (JDK), version 8 or higher - for example link:https://adoptopenjdk.net/[AdoptOpenJDK]
* The latest https://gradle.org/install[Gradle distribution]


== Create a project folder

Gradle comes with a built-in task, called `init`, that initializes a new Gradle project in an empty folder.
The `init` task uses the (also built-in) `wrapper` task to create a Gradle wrapper script, `gradlew`.

The first step is to create a folder for the new project and change directory into it.

[listing.terminal.sample-command]
----
$ mkdir demo
$ cd demo
----

== Run the init task

From inside the new project directory, run the `init` task using the following command in a terminal: `gradle init`.
When prompted, select the `2: application` project type and `4: Kotlin` as implementation language.
Afterwards, select `2: Add library projects`.
Next you can choose the DSL for writing buildscripts -  `1  : Groovy` or `2: Kotlin`.
For the other questions, press enter to use the default values.

The output will look like this:

[listing.terminal.sample-command,user-inputs="2|4|1|||"]
----
$ gradle init

Select type of project to generate:
1: basic
2: application
3: library
4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Split functionality across multiple subprojects?:
1: no - only one application project
2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 2

Select implementation language:
1: C++
2: Groovy
3: Java
4: Kotlin
5: Scala
6: Swift
Enter selection (default: Java) [1..6] 4

Select build script DSL:
1: Groovy
2: Kotlin
Enter selection (default: Groovy) [1..2] 1

Project name (default: demo):
Source package (default: demo):


BUILD SUCCESSFUL
2 actionable tasks: 2 executed
----

The `init` task generates the new project with the following structure:

[source.multi-language-sample,kotlin]
----
├── gradle // <1>
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew // <2>
├── gradlew.bat // <2>
├── settings.gradle.kts // <3>
├── buildSrc
│   ├── build.gradle.kts // <4>
│   └── src
│       └── main
│           └── kotlin // <5>
│               ├── demo.kotlin-application-conventions.gradle.kts
│               ├── demo.kotlin-common-conventions.gradle.kts
│               └── demo.kotlin-library-conventions.gradle.kts
├── app
│   ├── build.gradle.kts // <6>
│   └── src
│       ├── main // <7>
│       │   └── java
│       │       └── demo
│       │           └── app
│       │               ├── App.java
│       │               └── MessageUtils.kt
│       └── test // <8>
│           └── java
│               └── demo
│                   └── app
│                       └── MessageUtilsTest.kt
├── list
│   ├── build.gradle.kts // <6>
│   └── src
│       ├── main // <7>
│       │   └── java
│       │       └── demo
│       │           └── list
│       │               └── LinkedList.kt
│       └── test // <8>
│           └── java
│               └── demo
│                   └── list
│                       └── LinkedListTest.kt
└── utilities
├── build.gradle.kts // <6>
└── src
└── main // <7>
└── java
└── demo
└── utilities
├── JoinUtils.kt
├── SplitUtils.kt
└── StringUtils.kt
----

[source.multi-language-sample,groovy]
----
├── gradle // <1>
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew // <2>
├── gradlew.bat // <2>
├── settings.gradle // <3>
├── buildSrc
│   ├── build.gradle // <4>
│   └── src
│       └── main
│           └── groovy // <5>
│               ├── demo.kotlin-application-conventions.gradle
│               ├── demo.kotlin-common-conventions.gradle
│               └── demo.kotlin-library-conventions.gradle
├── app
│   ├── build.gradle // <6>
│   └── src
│       ├── main // <7>
│       │   └── java
│       │       └── demo
│       │           └── app
│       │               ├── App.java
│       │               └── MessageUtils.java
│       └── test // <8>
│           └── java
│               └── demo
│                   └── app
│                       └── MessageUtilsTest.java
├── list
│   ├── build.gradle // <6>
│   └── src
│       ├── main // <7>
│       │   └── java
│       │       └── demo
│       │           └── list
│       │               └── LinkedList.java
│       └── test // <8>
│           └── java
│               └── demo
│                   └── list
│                       └── LinkedListTest.java
└── utilities
├── build.gradle // <6>
└── src
└── main // <7>
└── java
└── demo
└── utilities
├── JoinUtils.java
├── SplitUtils.java
└── StringUtils.java
----

<1> Generated folder for wrapper files
<2> Gradle wrapper start scripts
<3> Settings file to define build name and subprojects
<4> Build script of _buildSrc_ to configure dependencies of the build logic
<5> Source folder for _convention plugins_ written in Groovy or Kotlin DSL
<6> Build script of the three subprojects - `app`, `list` and `utilities`
<7> Kotlin source folders in each of the subprojects
<8> Kotlin test source folders in the subprojects

You now have the project setup to build a Kotlin application which is modularized into multiple subprojects.

== Review the project files

The `settings.gradle(.kts)` file has two interesting lines:

====
include::sample[dir="kotlin",files="settings.gradle.kts[]"]
include::sample[dir="groovy",files="settings.gradle[]"]
====
- `rootProject.name` assigns a name to the build, which overrides the default behavior of naming the build after the directory it's in.
  It's recommended to set a fixed name as the folder might change if the project is shared - e.g. as root of a Git repository.
- `include("app", "list", "utilities")` defines that the build consists of three subprojects in the corresponding folders.
  More subprojects can be added by extending the list or adding more `include(...)` statements.

Since our build consists of multiple-subprojects, we want to share build logic and configuration between them.
For this, we utilize so-called _convention plugins_ that are located in the `buildSrc` folder.
Convention plugins in `buildSrc` are an easy way to utilise Gradle's plugin system to write reusable bits of build configuration.

in this sample, we can find three such convention plugins that are based on each other:

====
include::sample[dir="kotlin",files="buildSrc/src/main/kotlin/demo.kotlin-common-conventions.gradle.kts[]"]
include::sample[dir="groovy",files="buildSrc/src/main/groovy/demo.kotlin-common-conventions.gradle[]"]
====

The `kotlin-common-conventions` defines some configuration that should be shared by all our Kotlin project -- independent of whether they represent a library or the actual application.
First, we apply the link:https://kotlinlang.org/docs/reference/using-gradle.html[Kotlin Gradle plugin] *(1)* to have all functionality for building Kotlin projects available.
Then, we declare a repository -- `mavenCentral()` -- as source for external dependencies *(2)*, define dependency constraints *(3)* as well as standard dependencies that are shared by all subprojects and set JUnit 5 as testing framework *(4...)*.
Other shared settings, like compiler flags or JVM version compatibilities, could also be set here.

====
include::sample[dir="kotlin",files="buildSrc/src/main/kotlin/demo.kotlin-library-conventions.gradle.kts[]"]
include::sample[dir="groovy",files="buildSrc/src/main/groovy/demo.kotlin-library-conventions.gradle[]"]
====
====
include::sample[dir="kotlin",files="buildSrc/src/main/kotlin/demo.kotlin-application-conventions.gradle.kts[]"]
include::sample[dir="groovy",files="buildSrc/src/main/groovy/demo.kotlin-application-conventions.gradle[]"]
====


Both `kotlin-library-conventions` and `kotlin-application-conventions` apply the `kotlin-common-conventions` plugin *(1)* so that the configuration performed there is shared by library and application projects alike.
Next they apply the `java-library` or `application` plugin respectively *(2)* thus combining our common configuration logic with specifics for a library or application.
While there is no more fine grained configuration in this example, library or application project specific build configuration can go into one of these convention plugin scripts.

Lets have a look at the `build.gradle(.kts)` files in the subprojects.

====
include::sample[dir="kotlin",files="app/build.gradle.kts[]"]
include::sample[dir="groovy",files="app/build.gradle[]"]
====
====
include::sample[dir="kotlin",files="list/build.gradle.kts[]"]
include::sample[dir="groovy",files="list/build.gradle[]"]
====
====
include::sample[dir="kotlin",files="utilities/build.gradle.kts[]"]
include::sample[dir="groovy",files="utilities/build.gradle[]"]
====

Looking at the build scripts, we can see that they include up to three blocks

* Every build script should have a `plugins {}` block to apply plugins.
  In a well-structured build, it may only apply one _convention plugin_ as in this example.
  The convention plugin will then take care of applying and configuring core Gradle plugins (like `application` or `java-library`) other convention plugin or community plugins from the Plugin Portal.
* Second, if the project has dependencies, a `dependencies {}` block should be added.
  Dependencies can be external, such as the JUnit dependencies we add in `kotlin-common-conventions`, or can point to other local subprojects.
  For this, the `project(...)` notation is used.
  In our example, the `utilities` library requires the `list` library.
  And the `app` makes use of the `utilities` library.
  If local projects depend on each other, Gradle takes care of building dependent projects if (and only if) needed.
  To learn more, have a look at the documentation about link:{userManualPath}/core_dependency_management.html[dependency management in Gradle].
* Third, there may be one or multiple configuration blocks for plugins.
  These should only be used in build scripts directly if they configure something specific for the one project.
  Otherwise, such configurations also belong into a convention plugin.
  In this example, we use the `application {}` block, which is specific to the `application` plugin, to set the `mainClass` in our `app` project to `demo.app.App` *(1)*.

The last build file we have is the `build.gradle(.kts)` file in `buildSrc`.

====
include::sample[dir="kotlin",files="buildSrc/build.gradle.kts[]"]
include::sample[dir="groovy",files="buildSrc/build.gradle[]"]
====

This file is setting the stage to build the convention plugins themselves.
By applying one of the plugins for plugin development -- `groovy-gradle-plugin` or `kotlin-dsl` -- *(1)* we enable the support for writing convention plugins as build files in `buildSrc`.
Which are the convention plugins we already inspected above.
Furthermore, we add Gradle's plugin portal as repository *(2)*, which gives us access to community plugins.
To use a plugin it needs to be declared as dependency in the `dependencies {}` block.

Apart from the Gradle build files, you can find example Kotlin source code and test source code in the corresponding folders.
Feel free to modify these generated sources and tests to explore how Gradle reacts to changes when running the build as described next.

== Run the tests

You can use `./gradlew check` to execute all tests in all subprojects.
When you call Gradle with a plain task name like `check`, the task will be executed for all subprojects that provide it.
To target only a specific subproject, you can use the full path to the task.
For example `:app:check` will only execute the tests of the `app` project.
However, the other subprojects will still be compiled in the case of this example, because `app` declares dependencies to them.

[listing.terminal.sample-command]
----
$ ./gradlew check

BUILD SUCCESSFUL
9 actionable tasks: 9 executed
----

Gradle won't print more output to the console if all tests passed successfully.
You can find the test reports in the `<subproject>/build/reports` folders.
Feel free to change some of the example code or tests and rerun `check` to see what happens if a test fails.


== Run the application

Thanks to the `application` plugin, you can run the application directly from the command line.
The `run` task tells Gradle to execute the `main` method in the class assigned to the `mainClass` property.

[listing.terminal.sample-command]
----
$ ./gradlew run

> Task :app:run
Hello world!

BUILD SUCCESSFUL
2 actionable tasks: 2 executed
----

NOTE: The first time you run the wrapper script, `gradlew`, there may be a delay while that version of `gradle` is downloaded and stored locally in your `~/.gradle/wrapper/dists` folder.

== Bundle the application

The `application` plugin also bundles the application, with all its dependencies, for you.
The archive will also contain a script to start the application with a single command.

[listing.terminal.sample-command]
----
$ ./gradlew build

BUILD SUCCESSFUL in 0s
8 actionable tasks: 8 executed
----

If you run a full build as shown above, Gradle will have produced the archive in two formats for you:
`app/build/distributions/app.tar` and `app/build/distributions/app.zip`.

== Publish a Build Scan

The best way to learn more about what your build is doing behind the scenes, is to publish a link:https://scans.gradle.com[build scan].
To do so, just run Gradle with the `--scan` flag.

[listing.terminal.sample-command]
----
$ ./gradlew build --scan

BUILD SUCCESSFUL in 0s
8 actionable tasks: 8 executed

Publishing a build scan to scans.gradle.com requires accepting the Gradle Terms of Service defined at https://gradle.com/terms-of-service.
Do you accept these terms? [yes, no] yes

Gradle Terms of Service accepted.

Publishing build scan...
https://gradle.com/s/5u4w3gxeurtd2
----

Click the link and explore which tasks where executed, which dependencies where downloaded and many more details!

== Summary

That's it! You've now successfully configured and built a Kotlin application project with Gradle.
You've learned how to:

* Initialize a project that produces a Kotlin application
* Create a modular software project by combining multiple subprojects
* Share build configuration logic between subprojects using convention plugins in `buildSrc`
* Run similar named tasks in all subprojects
* Run a task in a specific subproject
* Build, bundle and run the application

== Next steps

When your project grows, you might be interested in more details how to configure JVM projects, structuring multi-project builds and dependency management:

- link:{userManualPath}/building_java_projects.html[Building Java & JVM projects]
- link:{userManualPath}/multi_project_builds.html[Working with multi-project builds]
- link:{userManualPath}/core_dependency_management.html[Dependency management in Gradle]
