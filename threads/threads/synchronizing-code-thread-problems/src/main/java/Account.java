/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Account {

  private int balance = 50;

  public int getBalance() {
    return balance;
  }

  public void withdraw(int amount) {
    balance = balance - amount;
  }
}
