package org.example.async

import kotlinx.coroutines.*
import java.util.concurrent.Executors

private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    println("Exception caught")
}
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(dispatcher + CoroutineName("name") + Job() + exceptionHandler)

fun main() {
    scope.launch {
        async {
            method()
        }
    }
    scope.launch {
        method2()
    }
}

suspend fun method() {
    delay(3000)
    error("")
}

suspend fun method2() {
    delay(5000)
    println("Method2 is finished")
}