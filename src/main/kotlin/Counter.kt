import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.p
import react.useState


val Counter = FC<Props> {
    val (count, setCount) = useState(0)

    p {
        +"The count is $count"
    }
    p {
        button {
            +"Increment"
            onClick = {
                setCount { c -> c + 1 }
            }
        }
    }
}