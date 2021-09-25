import Utils.{createUserFiles, getNum, outputUsers, writeToSubInputFile, writeUserFiles}

import java.io.{File, FileWriter}
import scala.io.Source

object Service {

  def initialize(implicit inputs: (Int, Int, Int)): Unit = {
    implicit val fWs: (IndexedSeq[FileWriter], IndexedSeq[FileWriter]) = createUserFiles
    val inputFile = Source.fromFile("src/main/resources/access.log")
    inputFile.getLines().foreach(writeToSubInputFile)
    val num = getNum
    val files1 = Range(0, num).map(i=>new File(s"index_input_$i.txt"))
    Range(0, num).foreach(i=>writeUserFiles(inputs._1, i, inputs._3, files1(i), fWs._2(i)))
  }


  def generateFile(n: Int)(implicit inputs: (Int, Int, Int)): Unit = {
    val tempFW = new FileWriter("temp.txt", false)
    val num = getNum
    val files2 = Range(0, num).map(i=>new File(s"index_output_$i.txt"))
    Range(0, num).foreach(i=>outputUsers(n-1, inputs._1, i, inputs._3, files2(i), tempFW))
    tempFW.close()
    println("please refer to temp.txt in project directory " +
      "for users which will be overwritten in subsequent entry")
  }
}
