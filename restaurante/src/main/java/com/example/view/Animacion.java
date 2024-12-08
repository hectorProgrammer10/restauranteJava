package com.example.view;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.example.controller.Monitor;
import com.example.controller.RestaurantSimulator;
import com.example.model.Comensal;
import com.example.model.Mesa;

import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Animacion extends GameApplication {
  private Monitor monitor;

  public Animacion() {

  }

  private RestaurantSimulator simulator;
  private AtomicInteger comensalCounter = new AtomicInteger(1);

  // Constructor adicional para recibir el simulador
  public Animacion(RestaurantSimulator simulator) {
    this.simulator = simulator;
  }

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setWidth(1280);
    settings.setHeight(800);
    settings.setTitle("Simulación Restaurante");
  }

  @Override
  protected void initGame() {
    // Configurar fondo y elementos iniciales
    Texture fondo = getAssetLoader().loadTexture("restaurante.png");
    entityBuilder().at(0, 0).view(fondo).buildAndAttach();
    Texture cocinero = getAssetLoader().loadTexture("cocinero.png");
    entityBuilder().at(950, 330).view(cocinero).buildAndAttach();

    List<Mesa> mesas = new ArrayList<>();
    for (int i = 0; i < 10; i++) { // Por ejemplo, 10 mesas
      mesas.add(new Mesa(i + 1));
      generarMesas(i);
    }
    monitor = new Monitor(mesas);
    this.simulator = new RestaurantSimulator(monitor);
    // Iniciar simulación
    iniciarSimulacion();
  }

  private void generarMesas(int id) {
    int inc = 100 * id;
    int mesaLugarX = 250 + inc;
    int mesaLugarY = 400;
    if (id > 2 && id < 6) {
      mesaLugarY = 530;
      mesaLugarX = -50 + inc;
    }
    if (id > 5) {
      mesaLugarY = 660;
      mesaLugarX = -350 + inc;
    }
    Texture mesa = getAssetLoader().loadTexture("mesa.png");
    Entity entidadMesa = entityBuilder()
        .at(mesaLugarX, mesaLugarY) // Punto inicial
        .view(mesa)
        .buildAndAttach();
  }

  private void iniciarSimulacion() {
    run(() -> {
      int idComensal = comensalCounter.getAndIncrement();
      Comensal comensal = new Comensal(idComensal);

      agregarComensalVisual(idComensal);
      simulator.llegadaComensal(comensal);
    }, Duration.seconds(2)); // Cada 3 segundos llega un nuevo comensal
  }

  private void agregarComensalVisual(int id) {
    int x = 50 * id;
    int lugar = 1100 - x;
    Texture texturaComensal = getAssetLoader().loadTexture("comensal.png");
    Entity entidadComensal = entityBuilder()
        .at(50, 800) // Punto inicial
        .view(texturaComensal)
        .buildAndAttach();

    // showMessage("Comensal " + id + " llegó al restaurante.");
    moverComensal(entidadComensal, lugar, 720); // Mover a la recepción
  }

  private void moverComensal(Entity comensal, double x, double y) {
    animationBuilder()
        .duration(Duration.seconds(1))
        .translate(comensal)
        .to(new Point2D(x, y))
        .buildAndPlay();
  }
}
