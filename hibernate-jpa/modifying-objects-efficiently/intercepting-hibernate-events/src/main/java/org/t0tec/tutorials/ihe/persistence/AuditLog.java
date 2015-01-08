package org.t0tec.tutorials.ihe.persistence;

import org.hibernate.EmptyInterceptor;
import org.hibernate.classic.Session;

import java.sql.Connection;

/**
 * The audit log helper that logs actual events. <p> The <tt>logEvent()</tt> method needs a JDBC
 * connection, it will open a new Hibernate <tt>Session</tt> on that connection and persist the
 * event. The temporary <tt>Session</tt> is then closed, transaction handling is left to the client
 * calling this method.
 */
public class AuditLog {

  public static void logEvent(
      String message,
      Auditable entity,
      Long userId,
      Connection connection) {

    Session tempSession =
        HibernateUtil.getSessionFactory()
            .openSession(connection, EmptyInterceptor.INSTANCE);

    try {
      AuditLogRecord record =
          new AuditLogRecord(message,
                             entity.getId(),
                             entity.getClass(),
                             userId);

      tempSession.save(record);
      tempSession.flush();

    } finally {
      tempSession.close();
    }
  }
}
