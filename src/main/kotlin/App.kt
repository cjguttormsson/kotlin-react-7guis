import csstype.ClassName
import kotlinx.browser.document
import react.StrictMode
import react.create
import react.dom.client.createRoot

fun main() {
    createRoot(document.getElementById("root") ?: error("No root!")).render(StrictMode.create {
        Launcher {}
    })
}

inline val String.cn get() = ClassName(this)

fun <T> List<T>.replacingElement(before: T, after: T): List<T> {
    val temp = this.toMutableList()
    temp[indexOf(before)] = after
    return temp.toList()
}