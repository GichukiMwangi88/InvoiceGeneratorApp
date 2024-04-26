import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class InvoiceGenerator
{
    private static ArrayList<Invoice> invoices = new ArrayList<Invoice>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        boolean exit = false;

        while(!exit)
        {
            displayMenu();
            int choice = scanner.nextInt();

            switch(choice)
            {
                case 1:
                    createInvoice();
                    break;
            }

        }
    }

    private static void displayMenu()
    {
        System.out.println("Welcome to the Invoice Generator");
        System.out.println("1. Create a new invoice");
        System.out.println("2. Add a line item to an invoice");
        System.out.println("3. Generate an invoice");
        System.out.println("4. Exit");
        System.out.println("Enter your choice");
    }

    private static void createInvoice()
    {
        System.out.println("Enter id of the client:");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter client name:");
        String name = scanner.nextLine();

        System.out.println("Enter client address:");
        String address = scanner.nextLine();

        System.out.println("Enter client phone number:");
        String phoneNumber = scanner.nextLine();

        Client client = new Client(clientId, name, address, phoneNumber);

        System.out.println("Enter id of the invoice:");
        int invoiceId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter date of Invoice (yyyy-MM-dd):");
        LocalDate invoiceDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter due date for invoice (yyyy-MM-dd):");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        Invoice invoice = new Invoice(invoiceId, client, invoiceDate, dueDate);
        invoices.add(invoice); //add invoice to the list of invoices

    }
}
