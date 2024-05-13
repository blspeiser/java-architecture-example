package com.infosociety.social.util;

import java.util.Objects;

/**
 * Pair.
 *
 * @author Baruch Speiser, Cambium.
 *
 * @param <X>
 * @param <Y>
 */
public class Pair<X, Y> {
  public final X x;
  public final Y y;

  /**
   * Constructor. 
   * @param x
   * @param y
   */
  public Pair(X x, Y y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return x.equals(pair.x) && y.equals(pair.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
  
  /**
   * IntegerPair.
   *
   * @author Baruch Speiser, Cambium.
   */
  public static class IntegerPair extends Pair<Integer, Integer> {
    /**
     * Constructor. 
     * @param x
     * @param y
     */
    public IntegerPair(int x, int y) {
      super(x, y);
    }
  }
}
