package lissajous;

public class LissajousData {

  protected static final int HF_DEF = 0;
  protected static final int VF_DEF = 0;
  protected static final int F_DEF = -1;
  private int horizFreq, vertFreq, phaseDifference;
  private LissajousCanvas canvas;

  public LissajousData() {
    horizFreq = HF_DEF;
    vertFreq = VF_DEF;
    phaseDifference = F_DEF;
  }

  /**
   * Get the value of horizFreq
   *
   * @return the value of horizFreq
   */
  public int getHorizFreq() {
    return horizFreq;
  }

  /**
   * Set the value of horizFreq
   *
   * @param horizFreq new value of horizFreq
   */
  public void setHorizFreq(int horizFreq) {
    this.horizFreq = horizFreq;
    canvas.draw();
  }

  public int getVertFreq() {
    return vertFreq;
  }

  public void setVertFreq(int vertFreq) {
    this.vertFreq = vertFreq;
    canvas.draw();
  }

  public int getPhaseDifference() {
    return phaseDifference;
  }

  public void setPhaseDifference(int phaseDifference) {
    this.phaseDifference = phaseDifference;
    canvas.draw();
  }

  public void setCanvas(LissajousCanvas canvas) {
    this.canvas = canvas;
  }
}
