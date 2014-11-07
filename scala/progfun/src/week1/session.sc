package week1

object session {
  1 + 2                                           //> res0: Int(3) = 3
  def abs(x: Double) = if (x < 0) -x else x       //> abs: (x: Double)Double

  def sqrt(x: Double) = {
    def sqrtIter(guess: Double, x: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess), x)

    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) / x < 0.001

    def improve(guess: Double) =
      (guess + x / guess) / 2
      
    sqrtIter(1.0, x)
  }                                               //> sqrt: (x: Double)Double
	
  sqrt(2)                                         //> res1: Double = 1.4142156862745097
  sqrt(4)                                         //> res2: Double = 2.000609756097561
  sqrt(1e-6)                                      //> res3: Double = 0.0010000001533016628
  sqrt(1e60)                                      //> res4: Double = 1.0000788456669446E30

  // Q: Why is the isGoodEnough test not very precise for small numbers and can lead to non-termination for very large numbers?
  // A: isGoodEnough not good for small numbers and for large numbers can be further apart from each other (absolute value)
  // ==> added / x before x < 0.001
  
  // Scoping & Blocks ==> good for removing redundant variables and also shorter & cleaner code
}