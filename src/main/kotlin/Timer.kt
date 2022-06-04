import kotlinx.js.timers.Timeout
import kotlinx.js.timers.clearInterval
import kotlinx.js.timers.setInterval
import react.*
import react.dom.aria.AriaRole
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import kotlin.js.Date
import kotlin.js.json
import kotlin.time.Duration.Companion.milliseconds

val Timer = FC<Props> {
    var startTimeMs: Double by useState(Date.now())
    var currentTimeMs: Double by useState(startTimeMs)
    var durationMs: Double by useState(10_000.0)

    val progress: Double = useMemo(currentTimeMs) { ((currentTimeMs - startTimeMs) / durationMs).coerceIn(0.0, 1.0) }

    useEffectOnce {
        val timeout: Timeout = setInterval(16.milliseconds) { currentTimeMs = Date.now() }
        cleanup {
            clearInterval(timeout)
        }
    }


    div {
        className = "row align-items-baseline justify-content-center mb-3".cn
        div {
            className = "col-4 text-end fs-1".cn
            +"Progress:"
        }
        div {
            className = "col-4 lh-base".cn
            div {
                className = "progress".cn
                div {
                    className = "progress-bar".cn
                    role = AriaRole.progressbar
                    style = json(
                        "width" to "${progress * 100.0}%", "transitionDuration" to "0.0s"
                    ).unsafeCast<CSSProperties>()
                }
            }
        }
    }
    div {
        className = "row justify-content-center mb-3".cn
        div {
            className = "col-4 text-end fs-1".cn
            +"Total Elapsed Time:"
        }
        div {
            className = "col-4 fs-1".cn
            +(((currentTimeMs - startTimeMs) / 1000).asDynamic().toFixed(1) as String)
            +" s"
        }
    }

    div {
        className = "row align-items-baseline justify-content-center mb-3".cn
        div {
            className = "col-4 text-end fs-1".cn
            +"Duration:"
        }
        div {
            className = "col-4 lh-base".cn
            input {
                className = "form-range".cn
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
    }
    div {
        className = "row justify-content-center mb-3".cn
        div {
            className = "col-4 d-flex".cn
            button {
                className = "btn btn-outline-primary".cn
                +"Reset Timer"
                onClick = {
                    startTimeMs = Date.now()
                }
            }

        }
    }
}