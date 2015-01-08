package org.t0tec.tutorials.ihe.persistence;

import org.hibernate.annotations.Immutable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Immutable
@Entity
@Table(name = "AUDIT_LOG")
public class AuditLogRecord {
  @Id
  @GeneratedValue
  @Column(name = "AUDIT_ID")
  private Long id = null;

  @Column(name = "MESSAGE", nullable = false, length = 255)
  public String message;
  @Column(name = "ENTITY_ID", nullable = false)
  public Long entityId;
  @Column(name = "ENTITY_CLASS", nullable = false)
  public Class entityClass;
  @Column(name = "USER_ID", nullable = false)
  public Long userId;
  @Column(name = "CREATED", nullable = false, updatable = false)
  public Date created;

  AuditLogRecord() {}

  public AuditLogRecord(String message, Long entityId, Class entityClass, Long userId) {
    this.message = message;
    this.entityId = entityId;
    this.entityClass = entityClass;
    this.userId = userId;
    this.created = new Date();
  }

  @Override
  public String toString() {
    return "AuditLogRecord{" +
           "created=" + created +
           ", id=" + id +
           ", message='" + message + '\'' +
           ", entityId=" + entityId +
           ", entityClass=" + entityClass +
           ", userId=" + userId +
           '}';
  }
}
