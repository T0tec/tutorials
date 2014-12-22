package org.t0tec.tutorials.helloworld;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.helloworld.persistence.HibernateUtil;

public class HelloWorld {

  private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

  public static void main(String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    helloWorld.sayHello();
  }

  public void sayHello() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();
    Message message = new Message("Hello World");
    Long helloWorldMessageId = (Long) session.save(message);
    tx.commit();
    session.close();

    // second unit of work
    getAllMessages();

    // Third unit of work
    Session thirdSession = HibernateUtil.getSessionFactory().openSession();
    Transaction thirdTransaction = thirdSession.beginTransaction();
    message = (Message) thirdSession.get(Message.class, helloWorldMessageId);
    message.setText("Greetings Earthling");
    message.setNextMessage(new Message("Take me to your leader (please)"));
    thirdTransaction.commit();
    thirdSession.close();

    // Fourth unit of work
    getAllMessages();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllMessages() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Message> messages =
        listAndCast(newSession.createQuery("from Message m order by m.text asc"));
    logger.debug("{} message(s) found", messages.size());
    for (Message msg : messages) {
      logger.debug(msg.toString());
    }
    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
