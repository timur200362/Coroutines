package org.example.callbacks

import org.example.entities.Author
import org.example.entities.Book
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import kotlin.concurrent.thread

object Display {

    private val infoArea = JTextArea().apply {
        isEditable = false
    }

    private val loadButton = JButton("Load Book").apply {
        addActionListener {
            isEnabled = false
            infoArea.text = "Loading book information...\n"
            loadBook { book ->
                infoArea.append("Book: ${book.title}\nYear: ${book.year}\nGenre: ${book.genre}\n")
                infoArea.append("Loading author information...\n")

                loadAuthor(book) { author ->
                    infoArea.append("Author: ${author.name}\nBiography: ${author.bio}\n")
                    isEnabled = true
                }
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
    }

    fun show() {
        mainFrame.isVisible = true
        startTimer()
    }

    private fun loadBook(callback: (Book) -> Unit) {
        thread {
            Thread.sleep(3000)
            callback(Book("1984", 1949, "Dystopia"))
        }
    }

    private fun loadAuthor(book: Book, callback: (Author) -> Unit) {
        thread {
            Thread.sleep(3000)
            callback(Author("George Orwell", "British writer and journalist"))
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