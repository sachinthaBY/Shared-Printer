package com.company;

import java.util.Random;
public class Student extends Thread{

    //initialize printer
    private Printer printer;
    //create single constructor for student
    public Student(ThreadGroup studentThreadGroup, String studentName, Printer printer){
        super(studentThreadGroup,studentName);
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++){
            //create paper count
            int paperCount = new Random().nextInt(19) + 1;
            //Generate document name
            String nameOfDocument = "Document by "+ this.getName();
            //Generate document ID
            String idOfDocument = "Document ID: DOC_"+ (i+1);

            //current document request to print
            Document requestToPrint = new Document(idOfDocument,nameOfDocument,paperCount);
            printer.printDocument(requestToPrint);

            try {
                sleep(new Random().nextInt(5000)+1000);     //sleep random times
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

