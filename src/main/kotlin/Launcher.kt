import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.p
import react.useState

enum class Gui(val displayName: String, val component: FC<*>) {
    CounterGui("Counter", Counter),
    TemperatureConverterGui("Temperature Converter", TemperatureConverter),
    FlightBookerGui("Flight Booker", FlightBooker),
    TimerGui("Timer", Timer),
    CRUDGui("CRUD", CRUD),
    CircleDrawerGui("Circle Drawer", CircleDrawer)
}

/** Container component to select a GUI */
val Launcher = FC<Props> {
    var selectedGui: Int? by useState(null)

    if (selectedGui == null) {
        // Show menu if nothing is selected
        for (gui in Gui.values()) {
            p {
                button {
                    +gui.displayName
                    onClick = {
                        selectedGui = gui.ordinal
                    }
                }
            }
        }
    } else {
        // Render selected gui component
        Gui.values()[selectedGui!!].component { }

        // Add a button to return to the menu
        p {
            button {
                +"Return to Launcher"
                onClick = {
                    selectedGui = null
                }
            }
        }
    }
}