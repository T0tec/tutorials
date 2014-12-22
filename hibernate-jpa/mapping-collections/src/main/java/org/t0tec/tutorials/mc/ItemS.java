package org.t0tec.tutorials.mc;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_S")
public class ItemS {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_S_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @ElementCollection(targetClass = java.lang.String.class)
  @JoinTable(name = "ITEM_S_IMAGE", joinColumns = @JoinColumn(name = "ITEM_S_ID"))
  @Column(name = "FILENAME", nullable = false)
  private Set<String> images = new HashSet<String>();

  public ItemS() {}

  public ItemS(String name) {
    this.name = name;
  }

  public Long getId() {
    return Id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getImages() {
    return this.images;
  }

  public void setImages(Set<String> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemS{" + getId() + "}, " + "name: " + getName() + ",  images size: "
        + getImages().size() + " ");

    sb.append("filenames: {");
    for (String s : images) {
      sb.append(s).append(", ");
    }

    sb.delete(sb.length() - 2, sb.length());

    sb.append("}");

    return sb.toString();
  }
}
