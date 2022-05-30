import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h4
import react.useState


val Counter = FC<Props> {
    val (count, setCount) = useState(0)

    h4 {
        +"The count is $count"
    }
    button {
        className = ClassName("btn btn-primary")
        +"Increment"
        onClick = {
            setCount { c -> c + 1 }
        }
    }
}