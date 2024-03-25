package com.company;

public class LaserPrinter implements ServicePrinter{

    //initialize laserPrinter variables
    private String printerID;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int numberOfDocumentPrinted;

    //crate threadGroups for students and technicians
    private ThreadGroup studentGroup;
    private ThreadGroup technicianGroup;

    public LaserPrinter(String printerID, ThreadGroup studentGroup, ThreadGroup technicianGroup) {
        this.printerID = printerID;
        this.studentGroup = studentGroup;
        this.technicianGroup = technicianGroup;
        this.currentPaperLevel = Full_Paper_Tray;
        this.currentTonerLevel = 50;
        this.numberOfDocumentPrinted = 0;
    }

    @Override
    public synchronized void printDocument(Document document) {
        String documentUserID = document.getUserID();
        String documentName = document.getDocumentName();
        int numberOfPages = document.getNumberOfPages();

        boolean insufficientPaperLevel = numberOfPages > currentPaperLevel;
        boolean insufficientTonerLevel = numberOfPages > currentTonerLevel;

        while (insufficientPaperLevel || insufficientTonerLevel) {
            // User cannot print
            if(insufficientPaperLevel && insufficientTonerLevel) {
                System.out.println(documentUserID+" by "+documentName+" Pages "+numberOfPages+" Out of paper and toner...Current Paper Level: "+currentPaperLevel+" and Toner Level: "+currentTonerLevel);
            }
            else if(insufficientPaperLevel) {
                System.out.println(documentUserID+" by "+documentName+" Pages "+numberOfPages+" Out of paper...Current Paper Level: "+currentPaperLevel);
            }
            else {
                System.out.println(documentUserID+" by "+documentName+" Pages "+numberOfPages+" Out of toner...Current Toner Level: "+currentTonerLevel);
            }
            try {
                wait();
                insufficientPaperLevel = numberOfPages > currentPaperLevel;
                insufficientTonerLevel = numberOfPages > currentTonerLevel;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(documentUserID+" by "+documentName+" Printing document with page: "+numberOfPages);
        currentPaperLevel -= numberOfPages;
        currentTonerLevel -= numberOfPages;
        numberOfDocumentPrinted++;
        System.out.println(documentUserID+" by "+documentName+" Pages "+numberOfPages+" Successfully printed document. New Paper Level: "+currentPaperLevel+" and Toner Level: "+currentTonerLevel);
    }

    @Override
    public synchronized void replaceTonerCartridge() {
        boolean cannotTonerReplaced = currentTonerLevel >= Minimum_Toner_Level;
        while (cannotTonerReplaced) {
            System.out.println("Checking toner...Toner no need replaced at this time. Current Toner Level is: "+ currentTonerLevel);
            try {
                wait(5000);
                if(!isCompletePrintJob()) {
                    cannotTonerReplaced = currentTonerLevel >= Minimum_Toner_Level;
                }
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Checking toner...Toner is low. Current Toner Level is: "+currentTonerLevel+" Replacing toner cartridge...");
        currentTonerLevel += PagesPerTonerCartridge;
        System.out.println("Completed replaced Toner Cartridge. New Toner Level is: " +currentTonerLevel);

        notifyAll(); // If this procedure is not used, the threads will continue to wait continuously.
        // will not execute
    }

    @Override
    public synchronized void refillPaper() {
        boolean cannotPaperRefilled = (currentPaperLevel + SheetsPerPack) > Full_Paper_Tray;
        while (cannotPaperRefilled) {
            System.out.println("Checking paper...Paper Tray no need to refilled at this time(exceeds maximum paper level)"+"\n"
                    +"Current paper level is: "+currentPaperLevel+" and Maximum paper level is: "+Full_Paper_Tray);
            try {
                wait(5000);
                if(!isCompletePrintJob()) {
                    cannotPaperRefilled = (currentPaperLevel + SheetsPerPack) > Full_Paper_Tray;
                }
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Allow paper technician to refill paper
        System.out.println("Checking paper level... Refilling printer with papers... ");
        currentPaperLevel += SheetsPerPack;
        System.out.println("Completed refilled tray with pack of paper. New Paper Level is: "+currentPaperLevel);

        notifyAll(); // If this procedure is not used, the threads will continue to wait continuously. (like Student threads)
        // will not execute
    }

    @Override
    public String toString() {
        return  "[ PrinterID : '" + printerID + '\'' +
                ", Paper Level : " + currentPaperLevel +
                ", Toner Level : " + currentTonerLevel +
                ", Documents Printed : " + numberOfDocumentPrinted + " ]";
    }

    private boolean isCompletePrintJob(){
        return studentGroup.activeCount() < 1;
    }
}
