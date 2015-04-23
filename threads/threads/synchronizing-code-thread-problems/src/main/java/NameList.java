import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
  The method Collections.synchronizedList() returns a List whose
  methods are all synchronized and "thread-safe" according to the documentation
  (like a Vector—but since this is the 21st century, we're not going to use a Vector
  here). The question is, can the NameList class be used safely from multiple threads?
  It's tempting to think that yes, since the data in names is in a synchronized
  collection, the NameList class is "safe" too. However that's not the case—the
  removeFirst() may sometimes throw a IndexOutOfBoundsException . What's
  the problem? Doesn't it correctly check the size() of names before removing
*/
public class NameList {

  private List names = Collections.synchronizedList(
      new LinkedList());

  public void add(String name) {
    names.add(name);
  }

  public String removeFirst() {
    if (names.size() > 0) {
      return (String) names.remove(0);
    } else {
      return null;
    }
  }
}
