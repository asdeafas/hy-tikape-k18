
package tikape.musicapplication.domain;

public class InvoiceData {
    
    private String customer;
    private int totalInvoices;
    private double totalSum;

    public InvoiceData(String customer, int totalInvoices, double totalSum) {
        this.customer = customer;
        this.totalInvoices = totalInvoices;
        this.totalSum = totalSum;
    }

    public String getCustomer() {
        return customer;
    }

    public int getTotalInvoices() {
        return totalInvoices;
    }

    public double getTotalSum() {
        return totalSum;
    }

}
