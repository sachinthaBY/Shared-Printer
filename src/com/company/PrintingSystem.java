package com.company;

public class PrintingSystem {

    public static void main(String[] args) {

        //Thread Groups : students & technicians
        ThreadGroup technicianGroup = new ThreadGroup("Technicians");
        ThreadGroup studentGroup = new ThreadGroup("Students");

        //An instance of a laser Printer class
        LaserPrinter laserPrinter = new LaserPrinter("HP 107A",studentGroup,technicianGroup);

        //4 Instances of the Student class
        Student student_1 = new Student(studentGroup,"Jackson",laserPrinter);
        Student student_2 = new Student(studentGroup,"Oliver",laserPrinter);
        Student student_3 = new Student(studentGroup,"Arthur",laserPrinter);
        Student student_4 = new Student(studentGroup,"Thomas",laserPrinter);

        System.out.println(student_1.getName()+","+
                student_2.getName()+","+
                student_3.getName()+","+
                student_4.getName()+" created students.");

        //An Instance of paper technician
        PaperTechnician paperTechnician = new PaperTechnician(technicianGroup,"Lily",laserPrinter);
        //An Instance of toner technician
        TonerTechnician tonerTechnician = new TonerTechnician(technicianGroup,"Mia",laserPrinter);

        System.out.println(paperTechnician.getName()+","+
                tonerTechnician.getName()+" created technicians.");

        //Starting the Students threads
        student_1.start();
        student_2.start();
        student_3.start();
        student_4.start();

        System.out.println(student_1.getName()+","+
                student_2.getName()+","+
                student_3.getName()+","+
                student_4.getName()+" students started printing.");

        //Starting the Technicians threads
        paperTechnician.start();
        tonerTechnician.start();

        System.out.println(paperTechnician.getName()+","+
                tonerTechnician.getName()+" technicians started.");

        //Wait until all the threads are executed
        try {
            student_1.join();
            System.out.println(student_1.getName()+" Completed printing!");

            student_2.join();
            System.out.println(student_2.getName()+" Completed printing!");

            student_3.join();
            System.out.println(student_3.getName()+" Completed printing!");

            student_4.join();
            System.out.println(student_4.getName()+" Completed printing!");

            paperTechnician.join();
            System.out.println("Completed paper technician!");

            tonerTechnician.join();
            System.out.println("Completed toner technician!");

            System.out.println("\n"+"\n"+"=========================================================================");
            System.out.println("===============Summery of the Printing System================"+"\n"+
                    laserPrinter.toString());
            System.out.println("=========================================================================");

        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}



