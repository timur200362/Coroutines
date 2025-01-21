package org.example.jobHierarchy

import kotlinx.coroutines.*
import java.util.concurrent.Executors

private val dispatcherIO = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val parent = Job()
private val scope = CoroutineScope(dispatcherIO + parent)

fun main() {
    var job2: Job? = null
    val job = scope.launch {
        launch {
            printNumber(1)
        }
        job2 = launch {
            printNumber(2)
        }
    }
    Thread.sleep(3000)
    job2?.cancel()
}

private suspend fun printNumber(number: Int) {
    while (true) {
        println(number)
        delay(1000)
    }
}