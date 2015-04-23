public class MultipleThreads {

    // 5 states
    // - new - after the Thread instance has been created but the run method has not been invoked --> alive
    // - runnable - it's eligible to run but has not been chosen by the scheduler, the run method has been invoked --> alive
    // - running - the state the thread is in when the scheduler selects it from the runnable pool,
    //             can transition out of the running state when the scheduler feels like it --> alive
    // - waiting/blocked/sleeping/ - the state when the thread is not eligible to run
    //                               the thread is still alive, but not running --> not runnable
    //                               but it might return to a runnable state later if particular events occur
    //                               one thread does NOT tell the other to wait/block/sleep
    // - dead - a thread is considered dead when its run() method completes.
    //          It's still a viable Thread object but it is no longer a separate thread of execution
    //          Once a thread is dead, it cannot be brought back to live
    public static void main(String[] args) {
//        testOne();
//        testTwo();
//        testThree();
        testFour();
    }

    // ################################ sleep method ################################
    // Guaranteed to cause the current thread to stop
    // executing for at least the specified sleep duration (although it might be
    // interrupted before its specified time).
    public static void testOne() {
        NameRunnable nameRunnable = new NameRunnable();

        Thread one = new Thread(nameRunnable);
        one.setName("Fred");

        Thread two = new Thread(nameRunnable);
        two.setName("Lucy");

        Thread three = new Thread(nameRunnable);
        three.setName("Ricky");

        // Still behavior is not guaranteed
        // Using sleep helps all threads get a chance to run
        // after sleep it goes back to the runnable state
        // still as the time expires there is no guarantee it will start running again as soon as possible
        one.start();
        two.start();
        three.start();
    }

    public static void testTwo() {
        CountThread ct = new CountThread();
        ct.run();
    }

    // ################################ Thread priority ################################
    // Threads always run with priority (number between 1-10 (range could be less)
    // Most of the JVM uses preemptive, priority based scheduling (time-slicing).
    // If so the JVM does not need a VM to implement a time-slicing scheduler (evenly chance)
    //
    // In most JVMs, however, the scheduler does use thread priorities in one important
    // way: If a thread enters the runnable state and it has a higher priority than any of the
    // threads in the pool and a higher priority than the currently running thread, the
    // lower-priority running thread usually will be bumped back to runnable and the highest-
    // priority thread will be chosen to run. In other words, at any given time, the currently
    // running thread usually will not have a priority that is lower than any of the threads
    // in the pool. In most cases, the running thread will be of equal or greater priority than the
    // highest-priority threads in the pool. This is as close to a guarantee about scheduling as
    // you'll get from the JVM specification, so you must never rely on thread priorities to
    // guarantee the correct behavior of your program.

    // When all threads have same priority the JVM might;
    // - pick a thread and run the thread until it blocks/completes
    // - time-slice the threads in the pool to give everyone an equal opportunity between the threads

    // thread will have the same priority as the main thread
    // the default priority is 5,
    public static void testThree() {
        MyThread myThread = new MyThread(); // th

        FooRunnable fooRunnable = new FooRunnable();
        Thread thread = new Thread(fooRunnable);
        thread.setPriority(8); // Thread.MIN_PRIORITY (1) Thread.MAX_PRIORITY (10) Thread.NORM_PRIORITY (5)
        thread.start();
    }

    // ################################ yield method ################################
    // What yield() is supposed to do is make the currently running
    // thread head back to runnable to allow other threads of the same priority to get their turn.
    // So the intention is to use yield() to promote graceful turn-taking among
    // equal-priority threads. In reality, though, the yield() method isn't guaranteed to do
    // what it claims, and even if yield() does cause a thread to step out of running and
    // back to runnable, there's no guarantee the yielding thread won't just be chosen again over
    // all the others! So while yield() might—and often does—make a running thread
    // give up its slot to another runnable thread of the same priority, there's no guarantee.
    // A yield() won't ever cause a thread to go to the waiting/sleeping/blocking state.
    // At most, a yield() will cause a thread to go from running to runnable, but
    // again, it might have no effect at all.

    // ################################ join method ################################
    // Guaranteed to cause the current thread to stop executing
    // until the thread it joins with (in other words, the thread it calls join() on)
    //completes, or if the thread it's trying to join with is not alive, the current
    // thread won't need to back out.
    // Example:
    // The non- static join() method of class Thread lets one thread "join onto the
    // end" of another thread. If you have a thread B that can't do its work until another
    // thread A has completed its work, then you want thread B to "join" thread A. This
    // means that thread B will not become runnable until A has finished (and entered the dead state).

    public static void testFour() {
        Thread thread = new Thread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
