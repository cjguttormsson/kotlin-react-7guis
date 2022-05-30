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

enum class Gui(val displayName: String, val component: FC<*>) {
    CounterGui("Counter", Counter), TemperatureConverterGui(
        "Temperature Converter",
        TemperatureConverter
    ),
    FlightBookerGui("Flight Booker", FlightBooker), TimerGui("Timer", Timer), CRUDGui(
        "CRUD",
        CRUD
    ),
    CircleDrawerGui("Circle Drawer", CircleDrawer)
}

/** Container component to select a GUI */
val Launcher = FC<Props> {
    var selectedGui: Int by useState(0)

    div {
        ul {
            className = ClassName("nav nav-tabs")
            for (gui in Gui.values()) {
                li {
                    className = ClassName("nav-item")
                    key = gui.ordinal.toString()
                    a {
                        className = ClassName("nav-link" + if (selectedGui == gui.ordinal) " active" else "")
                        if (selectedGui == gui.ordinal) {
                            ariaCurrent = AriaCurrent.page
                        }
                        href = "#"
                        onClick = { selectedGui = gui.ordinal }
                        +gui.displayName
                    }
                }
            }
        }

        div {
            className = ClassName("m-2")
            Gui.values()[selectedGui].component { }
        }
    }
}