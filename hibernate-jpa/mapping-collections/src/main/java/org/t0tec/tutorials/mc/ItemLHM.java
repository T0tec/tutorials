package org.t0tec.tutorials.mc;

import java.util.LinkedHashMap;
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

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "ITEM_LHM")
public class ItemLHM {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_LHM_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @ElementCollection
  @JoinTable(name = "ITEM_LHM_IMAGE", joinColumns = @JoinColumn(name = "ITEM_LHM_ID"))
  @MapKeyColumn(name = "IMAGENAME")
  @Column(name = "FILENAME")
  @OrderBy(clause = "lower(FILENAME) asc")
  private Map<String, String> images = new LinkedHashMap<String, String>();

  public ItemLHM() {}

  public ItemLHM(String name) {
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
    sb.append("ItemLHM{" + getId() + "}, " + "name: " + getName() + ",  images size: "
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
