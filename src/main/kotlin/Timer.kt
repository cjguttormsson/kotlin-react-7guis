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

val Timer = FC<Props> {
    var startTimeMs: Double by useState(Date.now())
    var currentTimeMs: Double by useState(startTimeMs)
    var durationMs: Double by useState(10_000.0)

    useEffectOnce {
        val timeout: Timeout = setInterval(16.milliseconds) { currentTimeMs = Date.now() }
        cleanup {
            clearInterval(timeout)
        }
    }

    p {
        +"Elapsed Time: "
        progress {
            value = (currentTimeMs - startTimeMs).roundToInt().toString()
            console.log("Before toDouble $durationMs")
            console.log(Duration.INFINITE)
            max = durationMs
            console.log("After toDouble")
        }
    }
    p {
        +(((currentTimeMs - startTimeMs) / 1000).asDynamic().toFixed(1) as String)
        +" s"
    }
    p {
        +"Duration: "
        input {
            type = InputType.range
            min = 1_000.0 // ms
            max = 60_000.0 // ms
            value = durationMs.toString()
            onChange = { e ->
                console.log(e.target.value)
                e.target.value.toDoubleOrNull()?.also { durationMs = it }
            }
        }
    }
    p {
        button {
            +"Reset"
            onClick = {
                startTimeMs = Date.now()
            }
        }
    }
}