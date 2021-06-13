package com.markoapps.weather.utils

import android.content.res.Resources
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.RestrictTo
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

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()