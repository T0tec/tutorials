class Reader extends Thread {

  Calculator c;

  public Reader(Calculator calc) {
    c = calc;
  }

  public void run() {

    synchronized (c) {
      try {
        System.out.println("Waiting for calculation...");
        c.wait();
      } catch (InterruptedException e) {
      }

      System.out.println("Total is: " + c.total);
    }
  }

  /*
    The program starts three threads that are all waiting to receive the finished
    calculation (lines 28–34) and then starts the calculator with its calculation.
    Note that if the run() method at line 42 used notify() instead of notifyAll(), only
    one reader would be notified instead of all the readers.
    */
  public static void main(String[] args) {
    Calculator calculator = new Calculator();
    new Reader(calculator).start();
    new Reader(calculator).start();
    new Reader(calculator).start();
    new Thread(calculator).start();
  }
}

class Calculator implements Runnable {

  int total;

  public void run() {
    synchronized (this) {
      for (int i = 0; i < 100; i++) {
        total += i;
      }

      notifyAll();
    }
  }
}
