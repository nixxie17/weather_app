package utils

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.LiveData
import io.kotest.core.spec.style.DescribeSpec

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(onChangeHandler)
    observe(observer, observer)
}

fun DescribeSpec.setupLiveData() {
    ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
        override fun executeOnDiskIO(runnable: Runnable) {
            runnable.run()
        }

        override fun postToMainThread(runnable: Runnable) {
            runnable.run()
        }

        override fun isMainThread(): Boolean {
            return true
        }
    })
}

fun DescribeSpec.resetLiveData() {
    ArchTaskExecutor.getInstance().setDelegate(null)
}