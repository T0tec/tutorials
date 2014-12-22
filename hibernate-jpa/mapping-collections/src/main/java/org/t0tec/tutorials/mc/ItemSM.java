package org.t0tec.tutorials.mc;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@Table(name = "ITEM_SM")
public class ItemSM {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_SM_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @ElementCollection
  @JoinTable(name = "ITEM_SM_IMAGE", joinColumns = @JoinColumn(name = "ITEM_SM_ID"))
  @MapKeyColumn(name = "IMAGENAME")
  @Column(name = "FILENAME")
  @Sort(type = SortType.NATURAL)
  // Or use custom Comparator
  private SortedMap<String, String> images = new TreeMap<String, String>();

  public ItemSM() {}

  public ItemSM(String name) {
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

  public SortedMap<String, String> getImages() {
    return this.images;
  }

  public void setImages(SortedMap<String, String> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemSM{" + getId() + "}, " + "name: " + getName() + ",  images size: "
        + getImages().size() + " ");

    sb.append("filenames: {");
    for (String s : images.values()) {
      sb.append(s).append(", ");
    }

    sb.delete(sb.length() - 2, sb.length());

    sb.append("}");

    return sb.toString();
  }
}
