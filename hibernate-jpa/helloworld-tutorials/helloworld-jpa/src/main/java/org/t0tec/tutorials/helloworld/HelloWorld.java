package org.t0tec.tutorials.helloworld;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HelloWorld {
  private static EntityManagerFactory emf;

  public static void main(String[] args) {
    firstUnitOfWork();

    secondUnitOfWork();

    // Shutting down the application
    emf.close();
  }

  private static void firstUnitOfWork() {
    // Start EntityManagerFactory
    emf = Persistence.createEntityManagerFactory("helloworld");
    // First unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Message message = new Message("Hello World");
    em.persist(message);

    tx.commit();
    em.close();
  }

  @SuppressWarnings("rawtypes")
  private static void secondUnitOfWork() {
    // Second unit of work
    EntityManager newEm = emf.createEntityManager();
    EntityTransaction newTx = newEm.getTransaction();
    newTx.begin();

    List messages =
        newEm.createQuery("select m from Message m order by m.text asc").getResultList();

    System.out.println(messages.size() + " message(s) found");

    for (Object m : messages) {
      Message loadedMsg = (Message) m;
      System.out.println(loadedMsg.getText());
    }

    newTx.commit();
    newEm.close();
  }
}
