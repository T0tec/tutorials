package org.t0tec.tutorials.mc;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_M")
public class ItemM {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_M_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @ElementCollection
  @JoinTable(name = "ITEM_M_IMAGE", joinColumns = @JoinColumn(name = "ITEM_M_ID"))
  @MapKeyColumn(name = "IMAGENAME")
  @Column(name = "FILENAME")
  private Map<String, String> images = new HashMap<String, String>();

  public ItemM() {}

  public ItemM(String name) {
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

  public Map<String, String> getImages() {
    return this.images;
  }

  public void setImages(Map<String, String> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemM{" + getId() + "}, " + "name: " + getName() + ",  images size: "
        + getImages().size() + ", ");

    sb.append("filenames: {");
    for (String s : images.values()) {
      sb.append(s).append(", ");
    }

    sb.delete(sb.length() - 2, sb.length());

    sb.append("}");

    return sb.toString();
  }
}
