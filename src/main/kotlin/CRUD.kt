import csstype.None
import emotion.react.css
import org.w3c.dom.HTMLSelectElement
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.select
import react.useRef
import react.useState

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

    p {
        +"Filter Prefix: "
        input {
            type = InputType.text
            value = filterPrefix
            onChange = { e ->
                filterPrefix = e.target.value
            }
        }
    }

    p {
        select {
            ref = selectElem
            size = 10
            for ((name, surname) in names) {
                option {
                    css {
                        // This approach keeps the indices in the select element in sync with the list of names
                        display = if (surname.startsWith(filterPrefix)) null else None.none
                    }
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

    p {
        +"Name: "
        input {
            type = InputType.text
            value = currentName
            onChange = { e -> currentName = e.target.value }
        }
    }

    p {
        +"Surname: "
        input {
            type = InputType.text
            value = currentSurname
            onChange = { e -> currentSurname = e.target.value }
        }
    }

    p {
        button {
            +"Create"
            onClick = {
                names = names.plusElement(currentName to currentSurname)
                currentName = ""
                currentSurname = ""
                selectElem.current!!.selectedIndex = -1 // deselect
            }
        }
        +" "
        button {
            +"Update"
            onClick = {
                val namesCopy = names.toMutableList()
                selectElem.current?.selectedIndex?.also { namesCopy[it] = currentName to currentSurname }
                names = namesCopy.toList()
            }
        }
        +" "
        button {
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