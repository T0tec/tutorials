package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    
    println()
    println(pascal(4, 4))
  }

  /**
   * Exercise 1 
   * If column equals or is less than 0 or columns equals row than return 1
   * To get an element of the Pascal's triangle, you need to add the previous row's 2 elements
   */
  def pascal(c: Int, r: Int): Int = {
      if (c <= 0 || c == r) 1
      else pascal(c, r - 1) + pascal(c - 1, r - 1)
  }


  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = ???

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
