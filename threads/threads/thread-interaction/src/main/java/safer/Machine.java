package safer;

import java.util.ArrayList;
import java.util.List;

class Machine extends Thread {

  List<MachineInstructions> jobs = new ArrayList<MachineInstructions>();

  public void addJob(MachineInstructions job) {
    synchronized (jobs) {
      jobs.add(job);
      jobs.notify();
    }
  }

  public void run() {
    while (true) {
      synchronized (jobs) {
        // wait until at least one job is available
        while (jobs.isEmpty()) {
          try {
            jobs.wait();
          } catch (InterruptedException ie) {
          }
        }
        // If we get here, we know that jobs is not empty
        MachineInstructions instructions = jobs.remove(0);
        // Send machine steps to hardware
      }
    }
  }
}
