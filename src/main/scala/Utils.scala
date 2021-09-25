import java.io.{File, FileWriter}
import scala.io.Source
import scala.util.Try

object Utils {

  def getNum(implicit inputs: (Int, Int, Int)): Int =
    (inputs._2-inputs._1)/inputs._3 + 1

  def createUserFiles(implicit inputs: (Int, Int, Int))
  : (IndexedSeq[FileWriter], IndexedSeq[FileWriter]) = {
    val num = getNum
    val indexedIFWs = Range(0, num)
      .map(i => new FileWriter(s"index_input_$i.txt", false))
    val indexedOFWs = Range(0, num)
      .map(i=> new FileWriter(s"index_output_$i.txt", false))
    (indexedIFWs, indexedOFWs)
  }


  def writeToSubInputFile(line: String)(implicit inputs: (Int, Int, Int),
                                       fWs: (IndexedSeq[FileWriter], IndexedSeq[FileWriter])): Unit = {
    val userId = Try(line.split(',')(1).toInt).getOrElse(-1)
    if(userId != -1) fWs._1(getNum((inputs._1, userId,  inputs._3))-1).write(line+"\n")
  }


  def writeUserFiles(start: Int, shard: Int, length: Int, iF: File, oFW: FileWriter): Unit = {
    val users: Array[collection.mutable.Set[String]] =
      Array.fill[collection.mutable.Set[String]](length)(collection.mutable.Set())
    val file = Source.fromFile(iF)
    file.getLines().foreach{line=>
      val fields = line.split(',')
      if(fields.length>2) {
        val user = fields.apply(1).toInt - start - shard * length
        users(user)+=fields(2)
      }
    }
    users.foreach(user=>oFW.write(user.mkString("", ",", "\n")))
    oFW.close()
  }


  def outputUsers(n: Int, start: Int, shard: Int, length: Int, iF: File, oFW: FileWriter): Unit = {
    val users = Source.fromFile(iF)
    users.getLines()
      .zipWithIndex
      .filter(x => x._1.split(',').length > n)
      .foreach(x =>oFW.write(shard*length+start+x._2+"\n"))
  }

}
