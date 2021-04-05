package com.application.runner

import java.util.concurrent.Executors

class TasksRunner(
    private val tasks: List<Runnable>
) {

    private val executorService = Executors.newFixedThreadPool(tasks.size)

    fun runTasks() {
        tasks
            .forEach { task ->
                executorService
                    .execute(task)
            }

        executorService.shutdown()
    }
}