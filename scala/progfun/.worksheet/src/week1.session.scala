package week1

object session {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(42); val res$0 = 
    1 + 2;System.out.println("""res0: Int(3) = """ + $show(res$0));$skip(46); 
    def abs(x: Double) = if (x < 0) -x else x;System.out.println("""abs: (x: Double)Double""");$skip(374); 

    def sqrt(x: Double) = {
        def sqrtIter(guess: Double, x: Double): Double =
            if (isGoodEnough(guess)) guess
            else sqrtIter(improve(guess), x)

        def isGoodEnough(guess: Double) =
            abs(guess * guess - x) / x < 0.001

        def improve(guess: Double) =
            (guess + x / guess) / 2

        sqrtIter(1.0, x)
    };System.out.println("""sqrt: (x: Double)Double""");$skip(14); val res$1 = 

    sqrt(2);System.out.println("""res1: Double = """ + $show(res$1));$skip(12); val res$2 = 
    sqrt(4);System.out.println("""res2: Double = """ + $show(res$2));$skip(15); val res$3 = 
    sqrt(1e-6);System.out.println("""res3: Double = """ + $show(res$3));$skip(15); val res$4 = 
    sqrt(1e60);System.out.println("""res4: Double = """ + $show(res$4));$skip(513); 

    // Q: Why is the isGoodEnough test not very precise for small numbers and can lead to non-termination for very large numbers?
    // A: isGoodEnough not good for small numbers and for large numbers can be further apart from each other (absolute value)
    // ==> added / x before x < 0.001

    // Scoping & Blocks ==> good for removing redundant variables and also shorter & cleaner code

    def gcd(a: Int, b: Int): Int = { // Tail recursive call
        if (b == 0) a
        else gcd(b, a % b)
    };System.out.println("""gcd: (a: Int, b: Int)Int""");$skip(19); val res$5 = 

    gcd(14, 21);System.out.println("""res5: Int = """ + $show(res$5));$skip(167); ;

    def factorial(n: Int): Int = { // Non-tail recursice call because of the multiplication at the end
        if (n == 0) 1
        else n * factorial(n - 1)
    };System.out.println("""factorial: (n: Int)Int""");$skip(19); val res$6 = 

    factorial(4);System.out.println("""res6: Int = """ + $show(res$6));$skip(185); 

    def factorial_tail_recursive(n: Int): Int = {
        def loop(acc: Int, n: Int): Int =
            if (n == 0) acc
            else loop(acc * n, n - 1)
        loop(1, n)
    };System.out.println("""factorial_tail_recursive: (n: Int)Int""");$skip(35); val res$7 = 

    factorial_tail_recursive(4);;System.out.println("""res7: Int = """ + $show(res$7))}

}
