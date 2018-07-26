package com.shh.plugin

import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.tasks.PackageApplication


import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginTest0 implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "PluginTest0 config start"

        def extension = project.extensions.create('pluginTest0Extension', PluginTest0Extension)

        project.task('PluginTest0') {
            doLast {
                println extension.message
            }
        }

        project.afterEvaluate {

            project.plugins.withId('com.android.application') {
                project.android.applicationVariants.all { variant ->
                    variant.outputs.each { variantOutput ->
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
        }

        println "PluginTest0 config end"
    }
}
