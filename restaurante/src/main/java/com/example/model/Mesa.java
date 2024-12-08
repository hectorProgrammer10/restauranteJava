package com.example.model;

public class Mesa {
  private boolean ocupada;
  private int id;

  // Constructor con ID
  public Mesa(int id) {
    this.id = id;
    this.ocupada = false;
  }

  public synchronized boolean ocupar() {
    if (!ocupada) {
      ocupada = true;
      return true;
    }
    return false;
  }

  public synchronized void liberar() {
    ocupada = false;
  }

  public boolean isOcupada() {
    return ocupada;
  }

  public int getId() {
    return id;
  }
}
