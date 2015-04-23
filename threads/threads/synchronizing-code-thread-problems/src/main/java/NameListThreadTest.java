/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */

/*
  What might happen here is that one of the threads will remove the one name and
  print it, and then the other will try to remove a name and get null . If we think just
  about the calls to names.size() and names.get(0) , they occur in this order:
  Thread t1 executes names.size() , which returns 1 .
  Thread t1 executes names.remove(0) , which returns Ozymandias .
  Thread t2 executes names.size() , which returns 0 .
  Thread t2 does not call remove(0).

  However, if we run the program again, something different might happen:
  Thread t1 executes names.size() , which returns 1 .
  Thread t2 executes names.size() , which returns 1 .
  Thread t1 executes names.remove(0) , which returns Ozymandias .
  Thread t2 executes names.remove(0) , which throws an exception because the
  list is now empty.

  The thing to realize here is that in a "thread-safe" class like the one returned by
  synchronizedList() , each individual method is synchronized. So names.size() is
  synchronized , and names.remove(0) is synchronized . But nothing prevents
  another thread from doing something else to the list in between those two calls. And
  that's where problems can happen.

  There's a solution here: Don't rely on Collections.synchronizedList() .
  Instead, synchronize the code yourself:

  public class NameList {
    private List names = new LinkedList();
    public synchronized void add(String name) {
      names.add(name);
    }
    public synchronized String removeFirst() {
      if (names.size() > 0)
      return (String) names.remove(0);
      else
      return null;
    }
  }
*/
public class NameListThreadTest {

  public static void main(String[] args) {
    final NameList nl = new NameList();
    nl.add("Ozymandias");
    class NameDropper extends Thread {

      public void run() {
        String name = nl.removeFirst();
        System.out.println(name);
      }
    }
    Thread t1 = new NameDropper();
    Thread t2 = new NameDropper();
    t1.start();
    t2.start();
  }
}
