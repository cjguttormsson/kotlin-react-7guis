import csstype.NamedColor
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p
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
    var returnDate: String by useState(departureDate)

    p {
        select {
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
    p {
        input {
            css {
                backgroundColor = if (dateFmt.matches(departureDate)) null else NamedColor.lightpink
            }
            value = departureDate
            onChange = { e ->
                departureDate = e.target.value
            }
        }
    }
    p {
        input {
            css {
                backgroundColor =
                    if (dateFmt.matches(returnDate) || flightType != FlightType.RoundTrip) null else NamedColor.lightpink
            }
            disabled = (flightType != FlightType.RoundTrip)
            value = if (flightType == FlightType.RoundTrip) returnDate else departureDate
            onChange = { e ->
                returnDate = e.target.value
            }
        }
    }
    p {
        button {
            +"Book Flight"
            disabled = !datesAreValid(
                departureDate, if (flightType == FlightType.RoundTrip) returnDate else departureDate
            )
            onClick = { console.log("Booked flight from $departureDate to $returnDate") }
        }
    }
}