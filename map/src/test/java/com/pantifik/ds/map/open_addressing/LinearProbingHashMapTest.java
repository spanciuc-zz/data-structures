package com.pantifik.ds.map.open_addressing;

public class LinearProbingHashMapTest extends OpenAddressingHashMapTest {

  @Override
  protected ProbingType createProbingInstance() {
    return ProbingType.LINEAR;
  }

}
