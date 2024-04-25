public class Service
{
    //Service name, service description and hourly rate
    private String serviceName;
    private String description;
    private double hourlyRate;

    public Service(String serviceName, String description, double hourlyRate)
    {
        this.serviceName = serviceName;
        this.description = description;
        this.hourlyRate = hourlyRate;

    }

    //Getters and Setters
    public String getServiceName()
    {
        return serviceName;
    }
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getHourlyRate()
    {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate)
    {
        this.hourlyRate = hourlyRate;
    }
}
