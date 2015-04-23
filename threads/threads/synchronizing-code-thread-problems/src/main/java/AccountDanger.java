/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class AccountDanger implements Runnable {

  private Account acct = new Account();

  /*  Preventing the Account Overdraw So what can be done? The solution is
    actually quite simple. We must guarantee that the two steps of the withdrawal—
    checking the balance and making the withdrawal—are never split apart. We need
    them to always be performed as one operation, even when the thread falls asleep in
    between step 1 and step 2! We call this an "atomic operation" (although the physics
    is a little outdated—in this case, "atomic" means "indivisible") because the
    operation, regardless of the number of actual statements (or underlying bytecode
    instructions), is completed before any other thread code that acts on the same data.
    You can't guarantee that a single thread will stay running throughout the entire
    atomic operation. But you can guarantee that even if the thread running the atomic
    operation moves in and out of the running state, no other running thread will be
    able to act on the same data. In other words, if Lucy falls asleep after checking the */
  public static void main(String[] args) {
    AccountDanger runnable = new AccountDanger();
    Thread one = new Thread(runnable);
    Thread two = new Thread(runnable);
    one.setName("Fred");
    two.setName("Lucy");
    one.start();
    two.start();
  }

  public void run() {
    for (int x = 0; x < 5; x++) {
      makeWithdrawal(10);
      if (acct.getBalance() < 0) {
        System.out.println("account is overdrawn!");
      }
    }
  }

  // So how do you protect the data? You must do two things:
  //  - Mark the variables private.
  //  - Synchronize the code that modifies the variables.
  private synchronized void makeWithdrawal(int amt) {
    if (acct.getBalance() >= amt) {
      System.out.println(Thread.currentThread().getName()
                         + " is going to withdraw");
      try {
        Thread.sleep(500);
      } catch (InterruptedException ex) {
      }
      acct.withdraw(amt);
      System.out.println(Thread.currentThread().getName() + " completes the withdrawal");
    } else {
      System.out.println("Not enough in account for " + Thread.currentThread().getName()
                         + " to withdraw " + acct.getBalance());
    }
  }

/*    - Only methods (or blocks) can be synchronized , not variables or classes.
      - Each object has just one lock.
      - Not all methods in a class need to be synchronized . A class can have both
        synchronized and non- synchronized methods.
      - If two threads are about to execute a synchronized method in a class and
        both threads are using the same instance of the class to invoke the method,
        only one thread at a time will be able to execute the method. The other
        thread will need to wait until the first one finishes its method call. In other
        words, once a thread acquires the lock on an object, no other thread can
        enter any of the synchronized methods in that class (for that object).
      - If a class has both synchronized and non- synchronized methods, multiple
        threads can still access the class's non- synchronized methods! If you have
        methods that don't access the data you're trying to protect, then you don't
        need to synchronize them. Synchronization can cause a hit in some cases (or
        even deadlock if used incorrectly), so you should be careful not to overuse it.
      - If a thread goes to sleep, it holds any locks it has—it doesn't release them.
      - A thread can acquire more than one lock. For example, a thread can enter a
        synchronized method, thus acquiring a lock, and then immediately invoke
        a synchronized method on a different object, thus acquiring that lock as
        well. As the stack unwinds, locks are released again. Also, if a thread acquires
        a lock and then attempts to call a synchronized method on that same
        object, no problem. The JVM knows that this thread already has the lock for
        this object, so the thread is free to call other synchronized methods on the
        same object, using the lock the thread already has.
      - You can synchronize a block of code rather than a method.*/
}
