package com.company;

import java.util.Random;
public class TonerTechnician extends Thread {

    private Printer printer;

    public TonerTechnician(ThreadGroup technicianGroup, String technicianName, Printer printer) {
        super(technicianGroup, technicianName);
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("Requesting to toner replace");
                ((LaserPrinter) printer).replaceTonerCartridge();
                sleep(new Random().nextInt(5000)+1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
