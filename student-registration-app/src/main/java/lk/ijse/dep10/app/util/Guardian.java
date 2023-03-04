package lk.ijse.dep10.app.util;

import lk.ijse.dep10.app.enums.GuardianType;

import java.io.Serializable;

public class Guardian implements Serializable {

    private String guardianName;
    private String guardianNIC;
    private String guardianAddress;
    private GuardianType guardianType;
    private String occupation;
    private String guardianMobile;
    private String guardianFixedLineNumber;
    private String guardianEmail;

    public Guardian() {
    }

    public Guardian(String guardianName, String guardianNIC,
                    String guardianAddress, GuardianType guardianType,
                    String occupation, String guardianMobile, String guardianFixedLineNumber,
                    String guardianEmail) {
        this.guardianName = guardianName;
        this.guardianNIC = guardianNIC;
        this.guardianAddress = guardianAddress;
        this.guardianType = guardianType;
        this.occupation = occupation;
        this.guardianMobile = guardianMobile;
        this.guardianFixedLineNumber = guardianFixedLineNumber;
        this.guardianEmail = guardianEmail;
    }


    /*getters*/
    public String getGuardianName() {
        return guardianName;
    }

    public String getGuardianNIC() {
        return guardianNIC;
    }

    public String getGuardianAddress() {
        return guardianAddress;
    }

    public GuardianType getGuardianType() {
        return guardianType;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getGuardianMobile() {
        return guardianMobile;
    }

    public String getGuardianFixedLineNumber() {
        return guardianFixedLineNumber;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }



    /*setters*/

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public void setGuardianNIC(String guardianNIC) {
        this.guardianNIC = guardianNIC;
    }

    public void setGuardianAddress(String guardianAddress) {
        this.guardianAddress = guardianAddress;
    }

    public void setGuardianType(GuardianType guardianType) {
        this.guardianType = guardianType;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setGuardianMobile(String guardianMobile) {
        this.guardianMobile = guardianMobile;
    }

    public void setGuardianFixedLineNumber(String guardianFixedLineNumber) {
        this.guardianFixedLineNumber = guardianFixedLineNumber;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }
}
