package org.example.coroutines

import kotlinx.coroutines.*
import org.example.entities.Author
import org.example.entities.Book
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

object Display {

    private val scope = CoroutineScope(CoroutineName("My coroutine") + Dispatchers.Unconfined)

    private val infoArea = JTextArea().apply {
        isEditable = false
    }

    private val loadButton = JButton("Load Book").apply {
        addActionListener {
            scope.launch {
                isEnabled = false
                infoArea.text = "Loading book information...\n"
                val book = loadBook()
                println("Loaded: $book")
                infoArea.append("Book: ${book.title}\nYear: ${book.year}\nGenre: ${book.genre}\n")
                infoArea.append("Loading author information...\n")
                val author = loadAuthor(book)
                println("Loaded: $author")
                infoArea.append("Author: ${author.name}\nBiography: ${author.bio}\n")
                isEnabled = true
            }
        }
    }

    private val timerLabel = JLabel("Time 00:00")
    private val topPanel = JPanel(BorderLayout()).apply {
        add(timerLabel, BorderLayout.WEST)
        add(loadButton, BorderLayout.EAST)
    }

    private val mainFrame = JFrame("Book and Author info").apply {
        layout = BorderLayout()
        add(topPanel, BorderLayout.NORTH)
        add(JScrollPane(infoArea), BorderLayout.CENTER)
        size = Dimension(400, 300)
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                scope.cancel()
            }
        })
    }

    fun show() {
        mainFrame.isVisible = true
        startTimer()
    }

    private fun longOperation() {
        mutableListOf<Int>().apply {
            repeat(300_000) {
                add(0, it)
            }
        }
    }

    private suspend fun loadBook(): Book {
        return withContext(Dispatchers.Default) {
            longOperation()
            Book("1984", 1949, "Dystopia")
        }
    }

    private suspend fun loadAuthor(book: Book): Author {
        return withContext(Dispatchers.Default) {
            longOperation()
            Author("George Orwell", "British writer and journalist")
        }
    }

    private fun startTimer() {
        var totalSeconds = 0
        while(true) {
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            timerLabel.text = String.format("Time: %02d:%02d", minutes, seconds)
            Thread.sleep(1000)
            totalSeconds++
        }
    }
}