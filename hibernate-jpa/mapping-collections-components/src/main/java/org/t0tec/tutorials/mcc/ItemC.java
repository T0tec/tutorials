package org.t0tec.tutorials.mcc;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ITEM_C")
public class ItemC {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_C_ID")
  private Long Id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @ElementCollection
  @JoinTable(name = "ITEM_C_IMAGE", joinColumns = @JoinColumn(name = "ITEM_C_ID"))
  @CollectionId(columns = @Column(name = "ITEM_C_IMAGE_ID"), type = @Type(type = "long"),
      generator = "sequence")
  private Collection<ImageC> images = new ArrayList<ImageC>();

  public ItemC() {}

  public ItemC(String name) {
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

  public Collection<ImageC> getImages() {
    return images;
  }

  public void setImages(Collection<ImageC> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Item{" + getId() + "}, " + "name: " + getName() + ",  images size: "
        + getImages().size() + ", ");

    sb.append("filenames: {");
    for (Object o : images) {
      ImageC i = (ImageC) o;
      sb.append(i.getFilename()).append(", ");
    }

    sb.delete(sb.length() - 2, sb.length());

    sb.append("}");

    return sb.toString();
  }
}
