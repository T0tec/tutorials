package org.t0tec.tutorials.cpp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Parent;

@Embeddable
public class Image {
  @Parent
  private Item item;
  @Column(length = 255, nullable = false)
  private String name;
  @Column(length = 255, nullable = false)
  private String filename;
  @Column(nullable = false)
  private int sizeX;
  @Column(nullable = false)
  private int sizeY;

  public Image() {}

  public Image(String name, String filename, int sizeX, int sizeY) {
    this.name = name;
    this.filename = filename;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }

  public Item getItem() {
    return item;
  }

  private void setItem(Item item) {
    this.item = item;
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

  public int getSizeX() {
    return sizeX;
  }

  public void setSizeX(int sizeX) {
    this.sizeX = sizeX;
  }

  public int getSizeY() {
    return sizeY;
  }

  public void setSizeY(int sizeY) {
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
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (getClass() != o.getClass()) {
      return false;
    }

    Image other = (Image) o;
    if (filename == null) {
      if (other.filename != null) {
        return false;
      }
    } else if (!filename.equals(other.filename)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (sizeX != other.sizeX) {
      return false;
    }
    if (sizeY != other.sizeY) {
      return false;
    }
    return true;
  }
}
