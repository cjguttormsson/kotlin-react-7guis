import kotlinx.js.timers.Timeout
import kotlinx.js.timers.clearInterval
import kotlinx.js.timers.setInterval
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.progress
import react.useEffectOnce
import react.useState
import kotlin.js.Date
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

val Timer = FC<Props> {
    var startTime: Double by useState(Date.now())
    var currentTime: Double by useState(startTime)
    var duration: Duration by useState(10_000.milliseconds)

    useEffectOnce {
        val timeout: Timeout = setInterval(100.milliseconds) { currentTime = Date.now() }
        cleanup {
            clearInterval(timeout)
        }
    }

    p {
        +"Elapsed Time: "
        progress {
            value = (currentTime - startTime).roundToInt().toString()
            console.log("Before toDouble $duration")
            console.log(Duration.INFINITE)
            max = duration.toDouble(DurationUnit.MILLISECONDS)
            console.log("After toDouble")
        }
    }
    p {
        +(((currentTime - startTime) / 1000).asDynamic().toFixed(1) as String)
        +" s"
    }
    p {
        +"Duration: "
        input {
            type = InputType.range
            min = 1_000.0 // ms
            max = 60_000.0 // ms
            value = duration.toDouble(DurationUnit.MILLISECONDS).toString()
            onChange = { e ->
                console.log(e.target.value)
                e.target.value.toDoubleOrNull()?.roundToInt()?. also { duration = it.milliseconds }
            }
        }
    }
    p {
        button {
            +"Reset"
            onClick = {
                startTime = Date.now()
            }
        }
    }
}