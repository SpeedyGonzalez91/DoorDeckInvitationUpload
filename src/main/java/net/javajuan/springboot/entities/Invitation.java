package net.javajuan.springboot.entities;

public class Invitation {
	private String kioskGroupId;
    private String location;
    private String reason;
    private String timeZone;

    public Invitation(){}

    public Invitation(String kioskGroupId, String location, String reason, String timeZone) {
        this.kioskGroupId = kioskGroupId;
        this.location = location;
        this.reason = reason;
        this.timeZone = timeZone;
    }

    public String getKioskGroupId() {
        return kioskGroupId;
    }

    public void setKioskGroupId(String kioskGroupId) {
        this.kioskGroupId = kioskGroupId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "kioskGroupId='" + kioskGroupId + '\'' +
                ", location='" + location + '\'' +
                ", reason='" + reason + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}

