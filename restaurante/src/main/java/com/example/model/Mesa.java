package com.example.model;

public class Mesa {
  private boolean ocupada;

  public Mesa() {
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
}
