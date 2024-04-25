import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UnitTest {
    @Test
    public void testInitialization() {
        var client = new Client(1234, "Robert", "123 St", "4804896309");
        assertEquals(1234, client.getId());
        client.setId(4568);
        assertEquals(4568, client.getId());

        assertEquals("Robert", client.getName());
        client.setName("Joe");
        assertEquals("Joe", client.getName());

        assertEquals("123 St", client.getAddress());
        client.setAddress("546 St");
        assertEquals("546 St", client.getAddress());

        assertEquals("4804896309", client.getContactInfo());
        client.setContactInfo("6232197009");
        assertEquals("6232197009", client.getContactInfo());


    }

    @Test
    public void testService() {
        var service = new Service("Software Development", "Custom software development services",
                100.0);
        assertEquals("Software Development", service.getServiceName());
        service.setServiceName("Backend Development");
        assertEquals("Backend Development", service.getServiceName());

        assertEquals("Custom software development services", service.getDescription());
        service.setDescription("Custom description");
        assertEquals("Custom description", service.getDescription());

        double expected = 100.0;
        double actual = service.getHourlyRate();

        assertEquals(expected, actual, 0.001);
        service.setHourlyRate(120.0);
        assertEquals(120.0, service.getHourlyRate(), 0.001);

    }

    @Test
    public void testLineItem() {
        var service = new Service("Software Dev", "testDescription", 100.0);
        int quantity = 1;
        var lineItem = new LineItem(service, quantity);

        //Test Getters

        String actualServiceName = lineItem.getService().getServiceName();
        assertEquals("Software Dev", actualServiceName);

        String actualDescription = lineItem.getService().getDescription();
        assertEquals("testDescription", actualDescription);

        double actualHourlyRate = lineItem.getService().getHourlyRate();
        assertEquals(100.0, actualHourlyRate, 0.001);

        assertEquals(100.0, lineItem.calculateSubtotal(quantity), 0.001);

    }

    @Test
    public void testInvoice()
    {
        var client1 = new Client(1234, "Robert", "123 St", "4804896309");
        LocalDate invoiceDate = LocalDate.of(2023, 10, 1);
        LocalDate dueDate = LocalDate.of(2023, 10, 30);
        var invoice = new Invoice(7890, client1, invoiceDate, dueDate);

        //Test Getters
        assertEquals(7890, invoice.getId(), 0.001);
        assertEquals(client1, invoice.getClient());
        assertEquals(invoiceDate, invoice.getInvoiceDate());
        assertEquals(dueDate, invoice.getDueDate());

        //Setters
        var client2 = new Client(5678, "Mwangi", "789 St", "4804896309");
        invoice.setClient(client2);
        assertEquals(client2, invoice.getClient());

        invoice.setId(9870);
        assertEquals(9870, invoice.getId(), 0.001);

        LocalDate invoiceDate2 = LocalDate.of(2023, 10, 20);
        invoice.setInvoiceDate(invoiceDate2);
        assertEquals(invoiceDate2, invoice.getInvoiceDate());

        LocalDate dueDate2 = LocalDate.of(2023, 10, 25);
        invoice.setDueDate(dueDate2);
        assertEquals(dueDate2, invoice.getDueDate());

        assertEquals(0, invoice.getLineItems().size());
        var service1 = new Service("Software Development", "testDescription", 100.0);
        int quantity1 = 1;
        ArrayList<LineItem> lineItems = new ArrayList<>();
        var lineItem1 = new LineItem(service1, quantity1);
        lineItems.add(lineItem1);
        invoice.setLineItems(lineItems);
        assertEquals(quantity1, invoice.getLineItems().size());

        assertEquals(100.0,lineItem1.calculateSubtotal(quantity1),0.001);
        assertEquals(100.0, invoice.calculateTotalAmount(), 0.001);

    }

    @Test
    public void testGenerateInvoice()
    {
        var client1 = new Client(7894, "Jane Doe", "123 St", "6232197009");
        LocalDate invoiceDate = LocalDate.of(2023, 10, 1);
        LocalDate dueDate = LocalDate.of(2023, 10, 30);
        var invoice = new Invoice(7890, client1, invoiceDate, dueDate);
        invoice.setClient(client1);

        var service1 = new Service("Software Development", "testDescription", 100.0);
        var service2 = new Service("Debugging Software", "debugDescription", 150.0);

        var lineItem1 = new LineItem(service1, 2);
        var lineItem2 = new LineItem(service2, 3);

        ArrayList<LineItem> lineItems = new ArrayList<>();
        lineItems.add(lineItem1);
        lineItems.add(lineItem2);
        invoice.setLineItems(lineItems);
        assertEquals(2, invoice.getLineItems().size());

        String expectedInvoice = """
         Invoice
         Invoice ID: 7890
         Client: Jane Doe
         Invoice Date: 2023-10-01
         Due Date: 2023-10-30
         Services:
         -------------------------------------------------------------------------
         Services             Description          Quantity             Subtotal
         -------------------------------------------------------------------------
         Software Development testDescription      2                    $200.00
         Debugging Software   debugDescription     3                    $450.00
            
         Total:                                                         $650.00

         Thank you for your business!""";

        assertEquals(expectedInvoice, invoice.generateInvoice());

    }
}

