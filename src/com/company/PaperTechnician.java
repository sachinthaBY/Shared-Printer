package com.company;

import java.util.Random;
public class PaperTechnician extends Thread{

    private Printer printer;

    public PaperTechnician(ThreadGroup technicianGroup, String technicianName, Printer printer){
        super(technicianGroup,technicianName);
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("Request to paper refill");
                ((LaserPrinter) printer).refillPaper();
                sleep(new Random().nextInt(5000)+1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}