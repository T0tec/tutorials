package org.t0tec.tutorials.helloworld;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MessageHandlerBean implements MessageHandler {
  @PersistenceContext
  EntityManager em;

  @Override
  public void saveMessages() {
    Message message = new Message("Hello World");
    em.persist(message);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void showMessages() {
    List messages = em.createQuery("select m from Message m order by m.text asc").getResultList();

    System.out.println(messages.size() + " message(s) found");

    for (Object m : messages) {
      Message loadedMsg = (Message) m;
      System.out.println(loadedMsg.getText());
    }
  }

}
