public class TestThreads {

    public static void main(String[] args) {
//        testOne();
//        testTwo();
        testThree();
    }

    public static void testOne() {
        // You'll need a Thread to do your work.
        Thread myThread = new MyThread();

        // "job-that-should-be-run-by-a-thread"
        // The job to be done
        MyRunnable myRunnable = new MyRunnable();

        // The worker that gets the job done
        // The runnable that you pass is aka "target or target runnable"
        Thread thread = new Thread(myRunnable);

        thread.start();

        Thread t = new Thread();
        t.start(); // Allowed, but this won't start a new thread
    }

    public static void testTwo() {
        NameRunnable nameRunnable = new NameRunnable();

        Thread thread = new Thread(nameRunnable);
        thread.setName("Fred");

        thread.start();
    }

    public static void testThree() {
        NameRunnable nameRunnable = new NameRunnable();

        Thread one = new Thread(nameRunnable);
        one.setName("Fred");

        Thread two = new Thread(nameRunnable);
        two.setName("Lucy");

        Thread three = new Thread(nameRunnable);
        three.setName("Ricky");

        // The behaviour is not guaranteed:
        // - they may not run in the orders you started them
        // - no guarantee that once a thread starts executing, it will keep executing until it's done
        // except:
        // Each thread will start and will run to completion
        // TODO: Test this by setting the x value to 500 or so
        one.start();
        two.start();
        three.start();
        // A thread stops being a thread when its target run() method completes
        // It's considered terminated/dead
        // Once a thread has been started it can never be started again (will cause an IllegalThreadStateException)
        // three.start
    }
}
