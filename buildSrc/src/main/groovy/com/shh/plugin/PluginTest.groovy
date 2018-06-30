package com.shh.plugin

import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.tasks.PackageApplication


import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginTest implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "PluginTest config start"

//        def extension = project.extensions.create('testExtension', TestPluginExtension)
//
//        project.task('pluginTest') {
//            doLast {
//                println extension.message
//            }
//        }

        project.afterEvaluate {
            println "PluginTest evaluate start"

            project.plugins.withId('com.android.application') {
                project.android.applicationVariants.all { ApplicationVariant variant ->
                    variant.outputs.each { ApkVariantOutput variantOutput ->
                        if (variantOutput.name.contains("release")) {
                            variantOutput.packageApplication.doFirst { PackageApplication task ->
                                project.copy {
                                    from "${project.projectDir.absolutePath}/pic/cat.jpg"
                                    into "${task.assets.asPath}"
                                }
                            }
                        }
                    }
                }
            }

            println("PluginTest evaluate end")
        }

        println "PluginTest config end"
    }
}
