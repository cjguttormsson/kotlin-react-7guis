import csstype.ClassName
import org.w3c.dom.HTMLSelectElement
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.span
import kotlin.js.json

val CRUD = FC<Props> {
    var filterPrefix: String by useState("")
    var names: List<Pair<String, String>> by useState(
        listOf(
            "Hans" to "Emil", "Max" to "Mustermann", "Roman" to "Tisch"
        )
    )
    var currentName: String by useState("")
    var currentSurname: String by useState("")

    val selectElem = useRef(null as HTMLSelectElement?)

    div {
        className = "row justify-content-center".cn

        div {
            className = "col-4".cn
            div {
                className = "input-group mb-3".cn
                span {
                    className = "input-group-text".cn
                    +"Filter Prefix: "
                }
                input {
                    className = "form-control".cn
                    type = InputType.text
                    value = filterPrefix
                    onChange = { e ->
                        filterPrefix = e.target.value
                    }
                }
            }
            div {
                className = "mb-3".cn
                select {
                    ref = selectElem
                    size = 10
                    style = json("width" to "100%").unsafeCast<CSSProperties>()
                    for ((name, surname) in names) {
                        option {
                            className = ClassName(if (surname.startsWith(filterPrefix)) "unfiltered" else "filtered")
                            +"$surname, $name"
                        }
                    }
                    onChange = { e ->
                        val idx = e.target.selectedIndex
                        currentName = names[idx].first
                        currentSurname = names[idx].second
                    }
                }
            }
        }

        div {
            className = "col-4 my-auto".cn
            div {
                className = "input-group mb-3".cn
                span {
                    className = "input-group-text".cn
                    +"Name"
                }
                input {
                    className = "form-control".cn
                    type = InputType.text
                    value = currentName
                    onChange = { e -> currentName = e.target.value }
                }
            }
            div {
                className = "input-group mb-3".cn
                span {
                    className = "input-group-text".cn
                    +"Surname"
                }
                input {
                    className = "form-control".cn
                    type = InputType.text
                    value = currentSurname
                    onChange = { e -> currentSurname = e.target.value }
                }
            }
        }
    }

    div {
        className = "row justify-content-start mx-auto".cn
        div {
            className = "col-2 offset-md-2".cn
            button {
                className = "btn btn-primary".cn
                style = json("width" to "100%").unsafeCast<CSSProperties>()
                +"Create"
                onClick = {
                    names = names.plusElement(currentName to currentSurname)
                    currentName = ""
                    currentSurname = ""
                    selectElem.current!!.selectedIndex = -1 // deselect
                }
            }
        }
        div {
            className = "col-2".cn
            button {
                className = "btn btn-outline-primary".cn
                style = json("width" to "100%").unsafeCast<CSSProperties>()
                +"Update"
                onClick = {
                    val namesCopy = names.toMutableList()
                    selectElem.current?.selectedIndex?.also { namesCopy[it] = currentName to currentSurname }
                    names = namesCopy.toList()
                }
            }
        }
        div {
            className = "col-2".cn
            button {
                className = "btn btn-outline-danger".cn
                style = json("width" to "100%").unsafeCast<CSSProperties>()
                +"Delete"
                onClick = {
                    val namesCopy = names.toMutableList()
                    selectElem.current?.selectedIndex?.also { namesCopy.removeAt(it) }
                    names = namesCopy.toList()
                    currentName = ""
                    currentSurname = ""
                }
            }
        }
    }
}