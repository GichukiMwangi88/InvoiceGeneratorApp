import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.sound.sampled.Line;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class InvoiceGenerator
{
    private static ArrayList<Invoice> invoices = new ArrayList<Invoice>();
    private static Scanner scanner = new Scanner(System.in);

    public InvoiceGenerator()
    {
        invoices = new ArrayList<Invoice>();
        scanner = new Scanner(System.in);
    }

    public ArrayList<Invoice> getInvoices()
    {
        return invoices;
    }

    public void generateInvoices()
    {
        boolean exit = false;

        while(!exit)
        {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  //consume the remaining newLine character

            switch(choice)
            {
                case 1:
                    createInvoice();
                    break;
                case 2:
                    displayInvoices();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the application.....");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }

        }
    }

    public void displayMenu()
    {
        System.out.println("Welcome to the Invoice Generator");
        System.out.println("1. Create a new invoice");
        System.out.println("2. Display Invoices");
        System.out.println("3. Exit");
        System.out.println("Enter your choice: ");
    }

    public void createInvoice()
    {
        //Prompt the user for invoice details
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

        //Prompt the user for more line items
        boolean addMoreLineItems = true;

        while(addMoreLineItems)
        {
            System.out.println("Enter the service name: ");
            String serviceName = scanner.nextLine();


            System.out.println("Enter the description of service:");
            String descriptionService = scanner.nextLine();

            System.out.println("Enter the hourly rate: ");
            double hourlyRate = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter the quantity:");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Service service = new Service(serviceName, descriptionService, hourlyRate);

            LineItem lineItem = new LineItem(service, quantity);
            lineItem.calculateSubtotal(quantity);
            invoice.addLineItems(lineItem);

            System.out.println("Add more line items? (Y/N): ");
            String choice = scanner.nextLine();
            addMoreLineItems = choice.equalsIgnoreCase("Y");
        }

        invoice.calculateTotalAmount();

        //Generate the invoice
        String invoiceString = invoice.generateInvoice();
        System.out.println(invoiceString);

        invoices.add(invoice);
    }


    public void displayInvoices()
    {
        if(invoices.isEmpty())
        {
            System.out.println("There are no invoices to display");
        }
        else
        {
            for (Invoice invoice : invoices)
            {
                //Display invoice details
                String invoiceString = invoice.generateInvoice();
                System.out.println(invoiceString);
                System.out.println("---------------------------");

            }

        }
    }

    public void saveInvoiceAsPDF(Invoice invoice, String filename) throws FileNotFoundException
    {
        try(Document document = new Document())
        {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Add invoice details to the PDF document
            document.add(new Paragraph("Invoice ID:" + invoice.getId()));
            document.add(new Paragraph("Client: " + invoice.getClient().getName()));
            document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate()));
            document.add(new Paragraph("Due Date: " + invoice.getDueDate()));
            document.add(new Paragraph(" "));

            // Add line items table to the PDF document
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Service Name"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Description"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Quantity"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Subtotal"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            for (LineItem lineItem : invoice.getLineItems())
            {
                table.addCell(lineItem.getService().getServiceName());
                table.addCell(lineItem.getService().getDescription());
                table.addCell(String.valueOf(lineItem.getQuantity()));
                table.addCell(String.format("%.2f", lineItem.calculateSubtotal(lineItem.getQuantity())));
            }

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: " + String.format("%.2f", invoice.getTotalAmount())));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Thank you for your business!"));

            document.close();
            System.out.println("Invoice saved as PDF successfully!");
        }
        catch (Exception e)
        {
            System.out.println("Error saving invoice as PDF: " + e.getMessage());
        }


    }


}
