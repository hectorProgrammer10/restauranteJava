package com.example.model;

public class Comensal {
  private final int id;
  private EstadoComensal atendido;

  public Comensal(int id) {
    this.id = id;
    this.atendido = EstadoComensal.ESPERANDO;
  }

  public int getId() {
    return id;
  }

  public EstadoComensal isAtendido() {
    return atendido;
  }

  public void setAtendido(EstadoComensal atendido) {
    this.atendido = atendido;
  }
}
