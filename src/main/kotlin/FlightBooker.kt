import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.useState
import kotlin.js.Date

enum class FlightType(val displayName: String) {
    OneWay("One-way flight"), RoundTrip("Round-trip flight");

    companion object {
        fun fromDisplayName(displayName: String) = values().firstOrNull { it.displayName == displayName }
    }
}

val dateFmt = Regex("^(\\d{2})\\.(\\d{2})\\.(\\d{4})\$")

/** Returns true if both the provided dates are valid and the return date is on or after the departure date. */
fun datesAreValid(departureDate: String, returnDate: String): Boolean = dateFmt.find(departureDate)?.let { depMatch ->
    val (depD, depM, depY) = depMatch.destructured
    dateFmt.find(returnDate)?.let { retMatch ->
        val (retD, retM, retY) = retMatch.destructured
        "$depY$depM$depD" <= "$retY$retM$retD"
    }
} ?: false

val FlightBooker = FC<Props> {
    var flightType: FlightType by useState(FlightType.OneWay)
    var departureDate: String by useState(Date().let {
        listOf(
            it.getDay().toString().padStart(2, '0'),
            it.getMonth().toString().padStart(2, '0'),
            it.getFullYear().toString().padStart(4, '0'),
        ).joinToString(".")
    })
    var bookMessage: String? by useState(null)

    var returnDate: String by useState(departureDate)
    div {
        className = "row justify-content-center m-3".cn

        div {
            className = "form-group col-sm-6".cn
            label {
                +"Flight Type"
            }
            select {
                className = "form-control form-control-lg".cn
                option {
                    +FlightType.OneWay.displayName
                }
                option {
                    +FlightType.RoundTrip.displayName
                }
                value = flightType.displayName
                onChange = { e ->
                    FlightType.fromDisplayName(e.target.value)?.also { flightType = it }
                }
            }
        }

    }

    div {
        className = "row justify-content-center m-3".cn

        div {
            className = "form-group col-sm-3".cn
            label {
                +"Departure Date"
            }
            input {
                className = "form-control form-control-lg".cn
                type = InputType.text
                value = departureDate
                onChange = { e ->
                    departureDate = e.target.value
                }
            }
        }
        div {
            className = "form-group col-sm-3".cn
            label {
                +"Return Date"
            }
            input {
                className = "form-control form-control-lg".cn
                disabled = (flightType != FlightType.RoundTrip)
                type = InputType.text
                value = if (flightType == FlightType.RoundTrip) returnDate else departureDate
                onChange = { e ->
                    returnDate = e.target.value
                }
            }
        }
    }

    div {
        className = "row justify-content-center m-3".cn

        div {
            className = "form-group col-sm-3".cn
            button {
                className = "form-control form-control-lg".cn
                +"Book Flight"
                type = ButtonType.submit
                disabled = !datesAreValid(
                    departureDate, if (flightType == FlightType.RoundTrip) returnDate else departureDate
                )
                onClick = { bookMessage = "Booked flight from $departureDate to $returnDate!" }
            }
        }
    }

    if (bookMessage != null) {
        div {
            className = "row justify-content-center m-3".cn
            +bookMessage!!
        }
    }
}