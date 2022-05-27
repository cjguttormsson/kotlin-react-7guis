import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.key
import react.useState

val TemperatureConverter = FC<Props> {
    var celsius: String by useState("-40.0")
    var fahrenheit: String by useState("-40.0")

    fun trySetC(celsiusString: String, valueTransform: (Double) -> Double = { it }) =
         celsiusString.toDoubleOrNull()?.let {
            val fixedCelsius = valueTransform(it).asDynamic().toFixed(1) as String
            celsius = fixedCelsius
            return fixedCelsius
        } ?: ""

    fun trySetF(fahrenheitString: String, valueTransform: (Double) -> Double = { it }) =
        fahrenheitString.toDoubleOrNull()?.let {
            val fixedFahrenheit = valueTransform(it).asDynamic().toFixed(1) as String
            fahrenheit = fixedFahrenheit
            return fixedFahrenheit
        } ?: ""

    p {
        input {
            key = "inputC"
            type = InputType.text
            value = celsius
            onChange = { e ->
                celsius = e.target.value
                trySetF(e.target.value, valueTransform = ::cToF)
            }
            onBlur = {
                trySetF(trySetC(celsius), valueTransform = ::cToF)
            }
        }
        +" Celsius = "
        input {
            key = "inputF"
            type = InputType.text
            value = fahrenheit
            onChange = { e ->
                fahrenheit = e.target.value
                trySetC(e.target.value, valueTransform = ::fToC)
            }
            onBlur = {
                trySetC(trySetF(fahrenheit), valueTransform = ::fToC)
            }
        }
        +" Fahrenheit"
    }
}

fun cToF(c: Double) = c * (9.0 / 5) + 32.0

fun fToC(f: Double) = (f - 32.0) * (5.0 / 9)
