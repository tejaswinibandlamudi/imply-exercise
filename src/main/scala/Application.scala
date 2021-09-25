import java.util.Scanner

object Application extends App {

  implicit val inputs: (Int, Int, Int) = (10000, 99999, 10000)
  Service.initialize
  val sc = new Scanner(System.in)
  while(true){
    try {
      println("Please enter no. of distinct paths")
      val n = sc.nextInt()
      Service.generateFile(n)
    }
    catch {
      case _: NumberFormatException =>
        println("Not a valid number please enter a valid number")
      case e =>
        println(s"Unknown exception $e")
    }
  }
}
