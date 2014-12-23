package org.t0tec.tutorials.mcc;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
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

  @ElementCollection
  @JoinTable(name = "ITEM_S_IMAGE", joinColumns = @JoinColumn(name = "ITEM_S_ID"))
  @AttributeOverride(name = "element.name", column = @Column(name = "IMAGENAME", length = 255,
      nullable = false))
  private Set<ImageS> images = new HashSet<ImageS>();

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

  public Set<ImageS> getImages() {
    return images;
  }

  public void setImages(Set<ImageS> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemS{" + getId() + "}, " + "name: " + getName() + ",  images size: "
        + getImages().size() + ", ");

    sb.append("filenames: {");
    for (ImageS i : images) {
      sb.append(i.getFilename()).append(", ");
    }

    sb.delete(sb.length() - 2, sb.length());

    sb.append("}");

    return sb.toString();
  }
}
