package com.example.model;

public class Comensal {
  private final int id;
  private boolean atendido;

  public Comensal(int id) {
    this.id = id;
    this.atendido = false;
  }

  public int getId() {
    return id;
  }

  public boolean isAtendido() {
    return atendido;
  }

  public void setAtendido(boolean atendido) {
    this.atendido = atendido;
  }
}
