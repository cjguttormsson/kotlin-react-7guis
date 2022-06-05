import org.w3c.dom.svg.SVGElement
import react.*
import react.dom.aria.AriaRole
import react.dom.aria.ariaLabel
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h5
import react.dom.html.ReactHTML.input
import react.dom.svg.ReactSVG.circle
import react.dom.svg.ReactSVG.svg
import kotlin.js.json

data class Circle(val x: Double, val y: Double, val r: Double = 40.0)

val CircleDrawer = FC<Props> {
    val (circles, setCircles) = useState<List<Circle>>(listOf())
    val (circleBeingEdited, setCircleBeingEdited) = useState(null as Circle?)
    val (newRadius, setNewRadius) = useState(0.0)
    val circlesToDraw: Sequence<Circle> = useMemo(circles, circleBeingEdited, newRadius) {
        sequence {
            for (circle in circles) {
                if (circle == circleBeingEdited) {
                    yield(Circle(circleBeingEdited.x, circleBeingEdited.y, newRadius))
                } else {
                    yield(circle)
                }
            }
        }
    }

    div {
        className = "row justify-content-center mb-3".cn
        div {
            className = "col-2".cn
            button {
                className = "btn btn-outline-secondary".cn
                +"Undo"
                disabled = true
            }
        }
        div {
            className = "col-2".cn
            button {
                className = "btn btn-primary".cn
                +"Redo"
                disabled = true
            }
        }
    }

    div {
        className = "row justify-content-center".cn
        div {
            className = "col-7".cn
            ReactHTML.style {
                +"circle:hover { fill: gray; }"
            }
            svg {
                style = json("border" to "1px solid black").unsafeCast<CSSProperties>()
                viewBox = "0 0 640 480"
                xmlns = "http://www.w3.org/2000/svg"
                for (c in circlesToDraw) {
                    circle {
                        cx = c.x
                        cy = c.y
                        r = c.r
                        fill = "white"
                        stroke = "black"
                        onContextMenuCapture = { e ->
                            e.preventDefault()
                            console.log("Clicked", c)
                            setNewRadius(c.r)
                            setCircleBeingEdited { c }
                        }
                        onClick = { e -> e.preventDefault() } // don't allow stacking circles
                    }
                }
            }
            onClick = { e ->
                val boundingClientRect = (e.nativeEvent.target as SVGElement).getBoundingClientRect()
                val scale = (boundingClientRect.right - boundingClientRect.left) / 640
                setCircles { old ->
                    old.plusElement(Circle(e.nativeEvent.offsetX / scale, e.nativeEvent.offsetY / scale))
                }
            }
        }
    }

    if (circleBeingEdited != null) {
        div {
            className = "modal".cn
            style = json("display" to "block").unsafeCast<CSSProperties>()
            role = AriaRole.dialog
            tabIndex = -1
            div {
                className = "modal-dialog".cn
                div {
                    className = "modal-content".cn
                    div {
                        className = "modal-header".cn
                        h5 {
                            +"Adjust diameter of circle at (${
                                circleBeingEdited.x.asDynamic().toFixed(1)
                            }, ${circleBeingEdited.y.asDynamic().toFixed(1)})"
                        }
                        button {
                            type = ButtonType.button
                            className = "btn-close".cn
                            ariaLabel = "Close"
                            onClick = {
                                setCircles { old ->
                                    old.replacingElement(
                                        circleBeingEdited, Circle(circleBeingEdited.x, circleBeingEdited.y, newRadius)
                                    )
                                }
                                setCircleBeingEdited(null)
                            }
                        }
                    }
                    div {
                        className = "modal-body".cn
                        form {
                            div {
                                className = "mb-3".cn
                                input {
                                    className = "form-range".cn
                                    type = InputType.range
                                    min = 10.0
                                    max = 120.0
                                    value = newRadius.toString()
                                    onChange =
                                        { e -> e.target.value.toDoubleOrNull()?.also { newR -> setNewRadius(newR) } }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}