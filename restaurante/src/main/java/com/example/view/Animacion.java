package com.example.view;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.example.controller.Monitor;
import com.example.controller.RestaurantSimulator;
import com.example.model.Comensal;
import com.example.model.Mesa;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.example.model.Poisson;

public class Animacion extends GameApplication {
  private Monitor monitor;

  public Animacion() {

  }

  private RestaurantSimulator simulator;
  private AtomicInteger comensalCounter = new AtomicInteger(1);
  private Poisson poisson = new Poisson(1); // comensales por segundo

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
    Texture recepcionista = getAssetLoader().loadTexture("recepcionista.png");
    entityBuilder().at(820, 670).view(recepcionista).buildAndAttach();
    Texture mesero = getAssetLoader().loadTexture("mesero.png");
    entityBuilder().at(620, 370).view(mesero).buildAndAttach();

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

    int inc = 100 * id; // *0 la 1ra */
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
    double nextArrivalTime = poisson.getNextArrivalTime();
    run(() -> {
      int idComensal = comensalCounter.getAndIncrement();
      Comensal comensal = new Comensal(idComensal);
      agregarComensalVisual(idComensal);
      simulator.llegadaComensal(comensal);
    }, Duration.seconds(nextArrivalTime)); // Cada 3 segundos llega un nuevo comensal
  }

  private void agregarComensalVisual(int id) {
    boolean mesaVacia = true;
    int mesaID = id;
    int x = 20 * id;
    int lugar = 1000 - x;
    Texture texturaComensal = getAssetLoader().loadTexture("comensal.png");
    Entity entidadComensal = entityBuilder()
        .at(50, 800) // Punto inicial
        .view(texturaComensal)
        .buildAndAttach();

    // showMessage("Comensal " + id + " llegó al restaurante.");
    runOnce(() -> {
      moverComensal(entidadComensal, lugar, 720);
    }, Duration.seconds(2)); // Mover a la recepción
    if (mesaVacia) {
      mesaVacia = false;
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> moverAMesa(entidadComensal, mesaID)));
      timeline.setCycleCount(1);
      timeline.play();
    }
  }

  private void moverComensal(Entity comensal, double x, double y) {

    animationBuilder()
        .duration(Duration.seconds(1))
        .translate(comensal)
        .to(new Point2D(x, y))
        .buildAndPlay();

  }

  private void moverAMesa(Entity comensal, int mesa) {
    int inc = 100 * (mesa - 1);
    int x;
    int y;
    int mesaLugarX = 240 + inc;
    int mesaLugarY = 400;
    switch (mesa) {
      case 1:
        x = mesaLugarX;
        y = mesaLugarY;
        break;
      case 2:
        x = mesaLugarX;
        y = mesaLugarY;
        break;
      case 3:
        x = mesaLugarX;
        y = mesaLugarY;
        break;
      case 4:
        x = -60 + inc;
        y = 530;
        break;
      case 5:
        x = -60 + inc;
        y = 530;
        break;
      case 6:
        x = -60 + inc;
        y = 530;
        break;
      case 7:
        x = -360 + inc;
        y = 660;
        break;
      case 8:
        x = -360 + inc;
        y = 660;
        break;
      case 9:
        x = -360 + inc;
        y = 660;
        break;
      case 10:
        x = -360 + inc;
        y = 660;
        break;
      default:
        x = 1100;
        y = 680;
        break;
    }
    runOnce(() -> {
      moverComensal(comensal, 1000, 720);
    }, Duration.seconds(1));
    runOnce(() -> {
      moverComensal(comensal, 860, 690);
    }, Duration.seconds(2));
    runOnce(() -> {
      moverComensal(comensal, 860, 600);
    }, Duration.seconds(2));
    runOnce(() -> {
      moverComensal(comensal, 660, 600);
    }, Duration.seconds(3));
    runOnce(() -> {
      moverComensal(comensal, x, y);
    }, Duration.seconds(4));
  }

}