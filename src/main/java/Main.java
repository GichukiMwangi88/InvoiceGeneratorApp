import javax.sound.sampled.Line;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Main
{
    public static void main(String[] args)
    {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

        //Create a sample client and invoice
        Client client = new Client(123, "Roberto", "123 St", "4804896309");
        Invoice invoice = new Invoice(789, client, LocalDate.now(), LocalDate.now().plusDays(15));

        //Add line items to invoice
        Service service1 = new Service("Web development", "Develop corp website", 120.00);
        LineItem lineitem1 = new LineItem(service1, 10);
        lineitem1.calculateSubtotal(10);
        invoice.addLineItems(lineitem1);

        Service service2 = new Service("SEO Optimization", "Improving website SEO", 100.0);
        LineItem lineItem2 = new LineItem(service2, 5);
        lineItem2.calculateSubtotal(5);
        invoice.addLineItems(lineItem2);

        invoice.calculateTotalAmount();

        // Specify the filename and path
        String fileName = "C:/Users/Mwangi/Desktop/test/test.pdf";

        //Save the invoice as PDF

        try
        {
            invoiceGenerator.saveInvoiceAsPDF(invoice, fileName);
            System.out.println("Invoice was successfully saved as PDF: " + fileName);

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Invoice was not successfully saved as PDF due to  " + e.getMessage());
        }

    }
}
