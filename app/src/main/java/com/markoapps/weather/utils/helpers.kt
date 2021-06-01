package com.markoapps.weather.utils

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


// convert task to suspend coroutine
/** Await the result of a task and return its result, or null if the task failed or was canceled. */
suspend fun <T> Task<T>.awaitResult() = suspendCoroutine<T?> { continuation ->
    if (isComplete) {
        if (isSuccessful) continuation.resume(this.result)
        else continuation.resume(null)
        return@suspendCoroutine
    }
    addOnSuccessListener { continuation.resume(this.result) }
    addOnFailureListener { continuation.resume(null) }
    addOnCanceledListener { continuation.resume(null) }
}