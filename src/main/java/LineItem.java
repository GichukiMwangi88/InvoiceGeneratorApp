public class LineItem
{
    //Represents a single line item in an invoice
    //Attributes
    private Service service;
    private int quantity;
    private double subTotal;

    public LineItem(Service service, int quantity)
    {
        this.service = service;
        this.quantity = quantity;
        this.subTotal = calculateSubtotal(quantity);

    }

    //Getters and setters methods
    public Service getService()
    {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        this.subTotal = calculateSubtotal(quantity);
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
        this.subTotal = calculateSubtotal(quantity);
    }

    //Calculate the subtotal
    public double calculateSubtotal(int quantity)
    {
        return service.getHourlyRate() * quantity;
    }
}
