import org.scalatest._

class MainSpec extends FlatSpec with Matchers {
  "Main" should "print 'Hello, World!'" in {
    val output = new java.io.ByteArrayOutputStream()
    Console.withOut(output) {
      Main.main(Array.empty)
    }
    output.toString.trim shouldEqual "Hello, World!"
  }
}