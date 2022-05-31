import csstype.ClassName
import react.FC
import react.Props
import react.dom.aria.AriaCurrent
import react.dom.aria.ariaCurrent
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.key
import react.useState

val guis = listOf(
    "Counter" to Counter,
    "Temperature Converter" to TemperatureConverter,
    "Flight Booker" to FlightBooker,
    "Timer" to Timer,
    "CRUD" to CRUD,
    "Circle Drawer" to CircleDrawer,
    "Cells" to null
)

/** Container component to select a GUI */
val Launcher = FC<Props> {
    val (selectedGui, setSelectedGui) = useState { Counter }

    div {
        className = ClassName("mb-3")
        ul {
            className = ClassName("nav nav-tabs")
            guis.forEach { (name, component) ->
                li {
                    className = ClassName("nav-item")
                    key = name
                    a {
                        className = ClassName(
                            "nav-link" + if (selectedGui == component) " active" else "" + if (component == null) " disabled" else ""
                        )
                        if (selectedGui == component) {
                            ariaCurrent = AriaCurrent.page
                        }
                        href = "#"
                        if (component != null) {
                            onClick = { setSelectedGui { component } }
                        }
                        +name
                    }
                }
            }
        }
    }

    selectedGui { }
}