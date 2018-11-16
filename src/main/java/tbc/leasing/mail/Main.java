package tbc.leasing.mail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert Csv Destination");
        String csvFilePath = scanner.next();

        File csv = new File(csvFilePath);

        if (!csv.exists()) {
            System.out.println("Csv file not found");
        }

        List<Contact> contacts = null;

        try {
            contacts = Sender.loadCsv(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // print contacts
        if (contacts != null) {

            System.out.println("Contacts Found ");
            for (Contact contact : contacts) {
                System.out.println(contact.toString());
            }

            System.out.println("Total Contacts Parsed " + contacts.size());


            String mailSubject = "თიბისი ლიზინგის 15 წლის იუბილე - მოსაწვევი";

            System.out.println("Insert Mail User Login");
            String mailUser = scanner.next();


            System.out.println("Insert Mail User Password");
            String mailPassword = scanner.next();


            System.out.println("TOTAL CONTACTS: " + (contacts.size()));
            System.out.println("CSV: " + csv.getAbsolutePath());
            System.out.println("SENDER USER: " + mailUser);
            System.out.println("SUBJECT: " + mailSubject);

            System.out.println("Type YES To Continue");

            String continueSend = scanner.next();

            if (continueSend.equals("YES")) {

                try {
                    Sender sender = new Sender(mailUser, mailPassword);

                    for (int i = 0; i < contacts.size(); i++) {

                        Thread.sleep(6000);

                        Contact contact = contacts.get(i);

                        File imageFile = contact.getImageFile();

                        if (imageFile.exists()) {
                            try {
                                sender.send(contact.getRecepientAddress(), mailSubject, imageFile);
                                System.out.println("Processed: " + contact.toString());
                            } catch (Exception e) {
                                System.out.println("Can No Process Send To: " + contact.toString() + " ERROR: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Image Not Found For Contact: " + contact.toString());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }


    }
}
