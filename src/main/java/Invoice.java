import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice
{
    //Attributes
    private int id;
    private Client client;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private ArrayList<LineItem> lineItems;
    private double totalAmount;

    public Invoice(int id, Client client, LocalDate invoiceDate, LocalDate dueDate)
    {
        this.id = id;
        this.client = client;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.lineItems = new ArrayList<>();
        this.totalAmount = 0;
    }

    //Getters and Setters
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public LocalDate getInvoiceDate()
    {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public ArrayList<LineItem> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineItems)
    {
        this.lineItems = lineItems;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    //Add and remove line items to the list of lineItems
    public void addLineItems(LineItem lineItem)
    {
        this.lineItems.add(lineItem);
    }

    public void removeLineItems(LineItem lineItem)
    {
        this.lineItems.remove(lineItem);
    }

    public double calculateTotalAmount()
    {
        for (LineItem lineItem : lineItems)
        {
            totalAmount += lineItem.calculateSubtotal(lineItem.getQuantity());
        }

        return totalAmount;
    }

    public String generateInvoice()
    {
        StringBuilder invoiceBuilder = new StringBuilder("Invoice\n");

        invoiceBuilder.append("Invoice ID: ").append(getId()).append("\n");
        invoiceBuilder.append("Client: ").append(getClient().getName()).append("\n");
        invoiceBuilder.append("Invoice Date: ").append(getInvoiceDate()).append("\n");
        invoiceBuilder.append("Due Date: ").append(getDueDate()).append("\n");
        invoiceBuilder.append("Services:\n");
        invoiceBuilder.append("-------------------------------------------------------------------------\n");
        invoiceBuilder.append("Services             Description          Quantity             Subtotal\n");
        invoiceBuilder.append("-------------------------------------------------------------------------\n");

        for (LineItem lineItem : lineItems)
        {
            String serviceName = lineItem.getService().getServiceName();
            String description = lineItem.getService().getDescription();
            int quantity = lineItem.getQuantity();
            double subtotal = lineItem.calculateSubtotal(quantity);

            invoiceBuilder.append(String.format("%-20s %-20s %-20d $%.2f\n",
                    serviceName, description, quantity, subtotal));
        }

        invoiceBuilder.append("\n");
        invoiceBuilder.append(String.format("%-10s%53s$%.2f\n","Total:","", calculateTotalAmount()));

        invoiceBuilder.append("\n");
        invoiceBuilder.append("Thank you for your business!");

        return invoiceBuilder.toString();

    }
}
