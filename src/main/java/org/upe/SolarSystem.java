package org.upe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem extends JPanel implements Runnable {
    private static final long serialVersionUID = -6923126786235441890L;
    private final int FPS = 60;
    private int panelWidth;
    private int panelHeight;
    private int middleWidth;
    private int middleHeight;
    private List<Planet> planetList;
    private Thread panelThread;

    public SolarSystem() {
        this(1024, 1024);
    }

    public SolarSystem(int width, int height) {
        panelWidth = width;
        panelHeight = height;
        middleWidth = panelWidth / 2;
        middleHeight = panelHeight / 2;
        panelThread = Thread.ofVirtual().name("solarSystemThread").unstarted(this);
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.planetList = new ArrayList<>();

        //mercury
        planetList.add(new Planet(Color.DARK_GRAY, 10, 1, 5));
        planetList.add(new Planet(Color.GRAY, 48, 2, 4));
        planetList.add(new Planet(Color.BLUE, 50, 3, 3));
        planetList.add(new Planet(Color.RED, 25, 4, 2));
    }

    @Override
    public void run() {
        while (panelThread.isAlive()) {
            update();
            repaint();
            try {
                Thread.sleep(1000/FPS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void update() {
        for (Planet planet : planetList) {
            planet.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int diameter = 48;
        int radius = diameter / 2;
        g2.setColor(Color.YELLOW);
        g2.fillOval(middleWidth - radius, middleHeight - radius, diameter, diameter);

        for(Planet planet :  planetList) {
            g2.setColor(planet.getColor());
            g2.fillOval(middleWidth + planet.getCoordX(), middleWidth + planet.getCoordY(), planet.getDiameter(), planet.getDiameter());
        }
        g2.dispose();
    }

    public void start() {
        panelThread.start();
    }

}
