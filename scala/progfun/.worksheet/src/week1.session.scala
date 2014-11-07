package week1

object session {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(40); val res$0 = 
  1 + 2;System.out.println("""res0: Int(3) = """ + $show(res$0));$skip(44); 
  def abs(x: Double) = if (x < 0) -x else x;System.out.println("""abs: (x: Double)Double""");$skip(335); 

  def sqrt(x: Double) = {
    def sqrtIter(guess: Double, x: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess), x)

    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) / x < 0.001

    def improve(guess: Double) =
      (guess + x / guess) / 2
      
    sqrtIter(1.0, x)
  };System.out.println("""sqrt: (x: Double)Double""");$skip(13); val res$1 = 
	
  sqrt(2);System.out.println("""res1: Double = """ + $show(res$1));$skip(10); val res$2 = 
  sqrt(4);System.out.println("""res2: Double = """ + $show(res$2));$skip(13); val res$3 = 
  sqrt(1e-6);System.out.println("""res3: Double = """ + $show(res$3));$skip(13); val res$4 = 
  sqrt(1e60);System.out.println("""res4: Double = """ + $show(res$4))}

  // Q: Why is the isGoodEnough test not very precise for small numbers and can lead to non-termination for very large numbers?
  // A: isGoodEnough not good for small numbers and for large numbers can be further apart from each other (absolute value)
  // ==> added / x before x < 0.001
  
  // Scoping & Blocks ==> good for removing redundant variables and also shorter & cleaner code
}
