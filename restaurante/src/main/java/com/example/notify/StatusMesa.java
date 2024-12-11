package com.example.notify;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class StatusMesa {
  private boolean status = true;

  public StatusMesa() {

  }

  public boolean getStatus() {
    System.out.println(status);
    return status;
  }
}
