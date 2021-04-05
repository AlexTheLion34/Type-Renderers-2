package com.application

import com.application.runner.TasksRunner
import com.application.task.PythonTask
import com.application.task.TimeTask
import java.lang.Exception
import java.util.*

fun repl() {

    fun checkPathToInterpreter(path: String) = try {
        Runtime
            .getRuntime()
            .exec(path)
            .destroy()
        true
    } catch (e: Exception) {
        println(e.message)
        println("Inter valid path!")
        false
    }

    val scanner = Scanner(System.`in`)

    while (true) {

        print("Enter path to Python interpreter: ")

        val pathToInterpreter = scanner.next()

        if (checkPathToInterpreter(path = pathToInterpreter)) {
            TasksRunner(
                tasks = listOf(
                    PythonTask(
                        pathToInterpreter = pathToInterpreter,
                        commandOptions = listOf(
                            "-m" to "timeit",
                            "-r" to "10"
                        )
                    ),
                    TimeTask()
                )
            ).runTasks()
            break
        }
    }

    scanner.close()
}

fun main() {
    repl()
}