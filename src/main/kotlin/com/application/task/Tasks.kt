package com.application.task

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.stream.Collectors

private val flag = AtomicBoolean(false)

class PythonTask(
    private val pathToInterpreter: String,
    private val commandOptions: List<Pair<String, String>>
) : Runnable {

    private fun buildCommand() = StringBuilder()
        .append("$pathToInterpreter ")
        .append(commandOptions
            .joinToString(
                separator = " ",
                transform = { option ->
                    "${option.first} ${option.second}"
                }
            )
        )
        .toString()

    override fun run() {

        val process = Runtime
            .getRuntime()
            .exec(
                buildCommand()
            )

        val reader = BufferedReader(
            InputStreamReader(process.inputStream)
        )

        val message = reader
            .lines()
            .collect(
                Collectors.joining("\n")
            )

        process.destroy()

        flag.set(true)

        println(message)
    }
}

class TimeTask(
    private val initialDelay: Long = 1000L,
    private val period: Long = 1000L
) : Runnable {

    override fun run() {

        val timer = Timer()

        timer
            .schedule(
                object : TimerTask() {

                    var time = 1

                    override fun run() {
                        if (!flag.get())
                            println(time++)
                        else {
                            this.cancel()
                            timer.cancel()
                        }
                    }

                },
                initialDelay,
                period
            )
    }
}