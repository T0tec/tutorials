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

        val chars = "())(".toList
        println(chars.count(_.equals('(')));
    }

    /**
     * Exercise 1  Pascal's triangle
     * If column equals or is less than 0 or columns equals row than return 1
     * To get an element of the Pascal's triangle, you need to add the previous row's 2 elements
     */
    def pascal(c: Int, r: Int): Int = {
        if (c <= 0 || c == r) 1
        else pascal(c, r - 1) + pascal(c - 1, r - 1)
    }

    /**
     * Exercise 2 Parentheses Balancing
     * Counting the parantheses
     */
    def balance(chars: List[Char]): Boolean = {
        def balanceIter(chars: List[Char], count: Int): Int = {
            if (chars.isEmpty) count
            else if (chars.head == '(')
                balanceIter(chars.tail, count + 1)
            else if (chars.head == ')' && count > 0)
                balanceIter(chars.tail, count - 1)
            else
                balanceIter(chars.tail, count)
        }

        if (chars.isEmpty) return false
        else false

        if (chars.count(_.equals('(')) == chars.count(_.equals(')'))) balanceIter(chars, 0) == 0
        else false

    }

    /**
     * Exercise 3 Counting Change
     */
    def countChange(money: Int, coins: List[Int]): Int = {
        def count(money: Int, coins: List[Int]): Int = {
            if (money == 0)
                1
            else if (money < 0)
                0
            else if (coins.isEmpty && money >= 1)
                0
            else
                count(money, coins.tail) + count(money - coins.head, coins)
        }

        count(money, coins.sortWith(_.compareTo(_) < 0))
    }

}
