package org.t0tec.tutorials.mc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_L")
public class ItemL {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_L_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @org.hibernate.annotations.CollectionOfElements
  @JoinTable(name = "ITEM_L_IMAGE", joinColumns = @JoinColumn(name = "ITEM_L_ID"))
  @org.hibernate.annotations.IndexColumn(name = "POSITION", base = 1)
  // position will start from 0 without base
  @Column(name = "FILENAME")
  private List<String> images = new ArrayList<String>();

  public ItemL() {}

  public ItemL(String name) {
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

  public List<String> getImages() {
    return this.images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemL{" + getId() + "}, " + "name: " + getName() + ",  images size: "
        + getImages().size() + " ");

    sb.append("filenames: {");
    for (Object o : images) {
      sb.append(o).append(", ");
    }

    sb.delete(sb.length() - 2, sb.length());

    sb.append("}");

    return sb.toString();
  }
}
