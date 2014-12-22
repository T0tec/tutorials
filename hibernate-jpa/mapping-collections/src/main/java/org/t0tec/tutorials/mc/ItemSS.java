package org.t0tec.tutorials.mc;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@Table(name = "ITEM_SS")
public class ItemSS {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_SS_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @ElementCollection(targetClass = java.lang.String.class)
  @JoinTable(name = "ITEM_SS_IMAGE", joinColumns = @JoinColumn(name = "ITEM_SS_ID"))
  @Column(name = "FILENAME", nullable = false)
  @Sort(type = SortType.NATURAL)
  // Or use custom Comparator
  private SortedSet<String> images = new TreeSet<String>();

  public ItemSS() {}

  public ItemSS(String name) {
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

  public SortedSet<String> getImages() {
    return this.images;
  }

  public void setImages(SortedSet<String> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemSS{" + getId() + "}, " + "name: " + getName() + ",  images size: "
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
