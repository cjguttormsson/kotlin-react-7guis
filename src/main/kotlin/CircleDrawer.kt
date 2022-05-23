import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.dialog
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.dom.svg.ReactSVG.circle
import react.dom.svg.ReactSVG.svg
import react.useState

data class Circle(val x: Double, val y: Double, val r: Double = 40.0) {}

val CircleDrawer = FC<Props> {
    val (circles, setCircles) = useState<List<Circle>>(listOf())
    val (circleBeingEdited, setCircleBeingEdited) = useState(null as Circle?)

    p {
        button {
            +"Undo"
            disabled = true
        }
        +" "
        button {
            +"Redo"
            disabled = true
        }
    }
    p {
        div {
            id = "circle-box"
            svg {
                viewBox = "0 0 640 480"
                xmlns = "http://www.w3.org/2000/svg"
                for (c in circles) {
                    circle {
                        cx = c.x
                        cy = c.y
                        r = c.r
                        fill = "white"
                        stroke = "black"
                        onContextMenuCapture = { e ->
                            e.preventDefault()
                            console.log("Clicked", c)
                            setCircleBeingEdited(c)
                        }
                    }
                }
            }
            onClick = { e ->
                setCircles { old ->
                    old.plusElement(Circle(e.nativeEvent.offsetX, e.nativeEvent.offsetY))
                }
            }
        }
    }

    dialog {
        open = circleBeingEdited != null
        +"Adjust diameter of circle at (${circleBeingEdited?.x}, ${circleBeingEdited?.y})"
        form {
            method = "dialog"
            input {
                type = InputType.range
                min = 0.0
                max = 100.0
                value = circleBeingEdited?.r?.toString() ?: "0.0"
                onChange = { e ->
                    e.target.value.toDoubleOrNull()?.also { newR ->
                        val newCircle = Circle(circleBeingEdited?.x ?: 0.0, circleBeingEdited?.y ?: 0.0, newR)
                        setCircles { old ->
                            old.map { if (it === circleBeingEdited) newCircle else it }
                        }
                        setCircleBeingEdited(newCircle)
                    }
                }
            }
            button {
                +"Okay"
                onClick = {
                    setCircleBeingEdited(null)
                }
            }
        }
    }
}