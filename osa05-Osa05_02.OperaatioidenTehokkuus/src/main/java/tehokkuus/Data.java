package tehokkuus;

import java.sql.Date;

public class Data {

    String ip, address, operation, user, device;
    Date date;

    public Data(String ip, String address, String operation, String user, String device, Date date) {
        this.ip = ip;
        this.address = address;
        this.operation = operation;
        this.user = user;
        this.device = device;
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
