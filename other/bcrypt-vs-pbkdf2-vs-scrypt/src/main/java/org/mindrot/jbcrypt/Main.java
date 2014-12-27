package org.mindrot.jbcrypt;

import net.crackstation.hashing.PasswordHash;

import com.lambdaworks.crypto.SCryptUtil;

public class Main {

  public static void main(String[] args) {
    String password = "passw0Rd";
    testBCrypt(password);
    testPBKDF2(password);
    testSCrypt(password);
  }

  public static void testBCrypt(String password) {
    // // gensalt's log_rounds parameter determines the complexity
    // // the work factor is 2**log_rounds, and the default is 10
    // String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

    long startTime = System.currentTimeMillis();

    try {
      // Print out 10 hashes
      for (int i = 0; i < 10; i++)
        System.out.println(BCrypt.hashpw(password, BCrypt.gensalt()));

      // Test password validation
      boolean failure = false;
      System.out.println("Running tests...");
      for (int i = 0; i < 100; i++) {
        password += i;
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        String secondHash = BCrypt.hashpw(password, BCrypt.gensalt());
        if (hash.equals(secondHash)) {
          System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
          failure = true;
        }
        String wrongPassword = "" + (i + 1);
        if (BCrypt.checkpw(wrongPassword, hash)) {
          System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
          failure = true;
        }
        if (!BCrypt.checkpw(password, hash)) {
          System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
          failure = true;
        }
      }
      if (failure)
        System.out.println("TESTS FAILED!");
      else
        System.out.println("TESTS PASSED!");
    } catch (Exception ex) {
      System.out.println("ERROR: " + ex);
    }

    long elapsedTime = System.currentTimeMillis() - startTime;

    System.out.println("BCrypt elapsed time: " + timeFormat(elapsedTime) + "ms");
  }

  public static void testPBKDF2(String password) {
    long startTime = System.currentTimeMillis();

    try {
      // Print out 10 hashes
      for (int i = 0; i < 10; i++)
        System.out.println(PasswordHash.createHash(password));

      // Test password validation
      boolean failure = false;
      System.out.println("Running tests...");
      for (int i = 0; i < 100; i++) {
        password += i;
        String hash = PasswordHash.createHash(password);
        String secondHash = PasswordHash.createHash(password);
        if (hash.equals(secondHash)) {
          System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
          failure = true;
        }
        String wrongPassword = "" + (i + 1);
        if (PasswordHash.validatePassword(wrongPassword, hash)) {
          System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
          failure = true;
        }
        if (!PasswordHash.validatePassword(password, hash)) {
          System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
          failure = true;
        }
      }
      if (failure)
        System.out.println("TESTS FAILED!");
      else
        System.out.println("TESTS PASSED!");
    } catch (Exception ex) {
      System.out.println("ERROR: " + ex);
    }

    long elapsedTime = System.currentTimeMillis() - startTime;

    System.out.println("PBKDF2 elapsed time: " + timeFormat(elapsedTime) + "ms");
  }

  public static void testSCrypt(String password) {
    long startTime = System.currentTimeMillis();

    try {
      // Print out 10 hashes
      for (int i = 0; i < 10; i++)
        System.out.println(SCryptUtil.scrypt(password, 16384, 8, 1));

      // Test password validation
      boolean failure = false;
      System.out.println("Running tests...");
      for (int i = 0; i < 100; i++) {
        password += i;
        String hash = SCryptUtil.scrypt(password, 16384, 8, 1);
        String secondHash = SCryptUtil.scrypt(password, 16384, 8, 1);
        if (hash.equals(secondHash)) {
          System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
          failure = true;
        }
        String wrongPassword = "" + (i + 1);
        if (SCryptUtil.check(wrongPassword, hash)) {
          System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
          failure = true;
        }
        if (!SCryptUtil.check(password, hash)) {
          System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
          failure = true;
        }
      }
      if (failure)
        System.out.println("TESTS FAILED!");
      else
        System.out.println("TESTS PASSED!");
    } catch (Exception ex) {
      System.out.println("ERROR: " + ex);
    }

    long elapsedTime = System.currentTimeMillis() - startTime;

    System.out.println("SCrypt elapsed time: " + timeFormat(elapsedTime) + "ms");
  }

  public static String timeFormat(final long time) {
    long minutes = time / 60000 % 60;
    long seconds = time / 1000 % 60;
    long milliseconds = time % 1000;

    return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
  }

}
