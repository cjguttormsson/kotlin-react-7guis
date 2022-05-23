import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
    createRoot(document.getElementById("root") ?: error("No root!")).render(Launcher.create())
}