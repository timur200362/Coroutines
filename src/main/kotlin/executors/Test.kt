package org.example.executors

import java.util.concurrent.Executors

fun main() {
    val executorService = Executors.newFixedThreadPool(1000)
    repeat(10_000) {
        executorService.execute {
            processImage(Image(it))
        }
    }
}

private fun processImage(image: Image) {
    println("Image $image is processing")
    Thread.sleep(1000)
    println("Image $image processed")
}

data class Image(val id: Int)