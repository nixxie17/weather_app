import com.weatherapp.currentweatherfeature.viewmodel.CurrentWeatherViewModel
import com.weatherapp.domain.model.Location
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository
import com.weatherapp.domain.usecase.GetWeatherByCityNameUseCase
import com.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import utils.TestDispatcherProvider
import utils.observeOnce
import utils.resetLiveData
import utils.setupLiveData

const val mockId = 1
const val mockCity = "London"
const val mockMain = "Drizzle"
const val mockDescription = "scattered clouds"
const val mockIcon = "some_icon.png"
const val mockTemp = 23.0
const val mockMinTemp = 11.0
const val mockMaxTemp = 25.0
const val mockHumidity = 345
const val mockPressure = 22

fun createWeatherInstance(): Weather = Weather(
    id = mockId,
    city = mockCity,
    main = mockMain,
    description = mockDescription,
    icon = mockIcon,
    temp = mockTemp,
    tempMin = mockMinTemp,
    tempMax = mockMaxTemp,
    pressure = mockPressure,
    humidity = mockHumidity
)

@ExperimentalCoroutinesApi
class CurrentWeatherViewModelSpec : DescribeSpec({

    val repository = mockk<WeatherRepository>()
    val getWeatherByLocationUseCase = GetWeatherByLocationUseCase(repository)
    val getWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(repository)
    val testCoroutineDispatcher = TestDispatcherProvider()
    val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher.io)

    lateinit var viewModel: CurrentWeatherViewModel

    beforeEach {
        Dispatchers.setMain(testCoroutineDispatcher.io)
        setupLiveData()
        viewModel = CurrentWeatherViewModel(
            testCoroutineDispatcher,
            getWeatherByLocationUseCase,
            getWeatherByCityNameUseCase
        )
    }

    afterEach {
        resetLiveData()
        clearMocks(repository)
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    describe("loading weather data by city location coordinates") {
        val location = Location(123.0, 345.0)

        it("viewModel emits expected live data value") {
            runBlockingTest {
                coEvery {
                    repository.getWeatherByLocation(location)
                } answers { createWeatherInstance() }
            }
            viewModel.loadWeatherByLocation(location)
            coVerify { repository.getWeatherByLocation(any()) }
            viewModel.weatherViewState.observeOnce {
                expectThat(it.city).isEqualTo(mockCity)
                expectThat(it.description).isEqualTo(mockDescription)
                expectThat(it.main).isEqualTo(mockMain)
                expectThat(it.temp).isEqualTo(mockTemp)
                expectThat(it.tempMax).isEqualTo(mockMaxTemp)
                expectThat(it.tempMin).isEqualTo(mockMinTemp)
                expectThat(it.humidity).isEqualTo(mockHumidity)
                expectThat(it.pressure).isEqualTo(mockPressure)

            }
        }
        //failure usecases should be tested as well
    }

    describe("loading weather data by city name"){
        val cityName = "London"

        it("viewModel emits expected live data value") {
            runBlockingTest {
                coEvery {
                    repository.getWeatherByCityName(cityName)
                } answers { createWeatherInstance() }
            }
            viewModel.loadWeatherByCityName(cityName)
            coVerify { repository.getWeatherByCityName(any()) }
            viewModel.weatherViewState.observeOnce {
                expectThat(it.city).isEqualTo(mockCity)
                expectThat(it.description).isEqualTo(mockDescription)
                expectThat(it.main).isEqualTo(mockMain)
                expectThat(it.temp).isEqualTo(mockTemp)
                expectThat(it.tempMax).isEqualTo(mockMaxTemp)
                expectThat(it.tempMin).isEqualTo(mockMinTemp)
                expectThat(it.humidity).isEqualTo(mockHumidity)
                expectThat(it.pressure).isEqualTo(mockPressure)

            }
        }
        //failure usecases should be tested as well
    }
})