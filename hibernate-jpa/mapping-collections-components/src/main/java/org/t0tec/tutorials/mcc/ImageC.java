package org.t0tec.tutorials.mcc;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Parent;

@Embeddable
public class ImageC {
  @Parent
  private ItemC itemC;
  @Column(length = 255, nullable = false)
  private String name;
  @Column(length = 255, nullable = false)
  private String filename;
  @Column
  private Integer sizeX;
  @Column
  private Integer sizeY;

  public ImageC() {}

  public ImageC(String name, String filename, Integer sizeX, Integer sizeY) {
    this.name = name;
    this.filename = filename;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }

  public ItemC getItemC() {
    return itemC;
  }

  public void setItemC(ItemC item) {
    this.itemC = item;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Integer getSizeX() {
    return sizeX;
  }

  public void setSizeX(Integer sizeX) {
    this.sizeX = sizeX;
  }

  public Integer getSizeY() {
    return sizeY;
  }

  public void setSizeY(Integer sizeY) {
    this.sizeY = sizeY;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((filename == null) ? 0 : filename.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + sizeX;
    result = prime * result + sizeY;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null)
      return false;
    if (getClass() != o.getClass())
      return false;

    ImageC other = (ImageC) o;
    if (filename == null) {
      if (other.filename != null)
        return false;
    } else if (!filename.equals(other.filename))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (sizeX != other.sizeX)
      return false;
    if (sizeY != other.sizeY)
      return false;
    return true;
  }
}
