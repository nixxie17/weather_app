package utils

import com.weatherapp.commons.DispatcherProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherProvider(testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    DispatcherProvider {
    override val default = testDispatcher
    override val main = testDispatcher
    override val io = testDispatcher
}