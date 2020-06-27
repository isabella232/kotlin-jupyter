package org.jetbrains.kotlin.jupyter.test

import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlin.mainKts.impl.IvyResolver
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.dependencies.ExternalDependenciesResolver
import kotlin.script.experimental.dependencies.addRepository
import kotlin.test.assertTrue

class ResolverTests {

    fun ExternalDependenciesResolver.doResolve(artifact: String): List<File> {
        this.addRepository("https://jcenter.bintray.com/")
        this.addRepository("https://repo.maven.apache.org/maven2/")
        this.addRepository("https://jitpack.io")
        assertTrue(acceptsArtifact(artifact))
        val result = runBlocking { resolve(artifact) }
        assertTrue(result is ResultWithDiagnostics.Success)
        return result.value
    }

    @Test
    fun GetGgplotTest() {
        val files = IvyResolver().doResolve("org.apache.spark:spark-mllib_2.11:2.4.4")
        println("Downloaded files: ${files.count()}")
        files.forEach {
            println(it)
        }

    }
}
