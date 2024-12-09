package com.example.model;

import java.util.Random;

public class Poisson {
  private final double lambda;
  private final Random random;

  public Poisson(double lambda) {
    this.lambda = lambda;
    this.random = new Random();
  }

  public double getNextArrivalTime() {
    return -Math.log(1.0 - random.nextDouble()) / lambda;
  }
}
