package vaqpack.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_Employer {
    private final StringProperty email, firstName, middleName, lastName, title,
            company, address1, address2, city, state, zip, adSource, adPosition,
            adRefNumb;
    
    public VP_Employer(String email, String firstName, String middleName, String lastName,
            String title, String company, String address1, String address2, String city,
            String state, String zip, String adSource, String adPosition, String adRefNumb) {
        //-------- Initialization Start ----------\\
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
        this.title = new SimpleStringProperty(title);
        this.company = new SimpleStringProperty(company);
        this.address1 = new SimpleStringProperty(address1);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zip = new SimpleStringProperty(zip);
        this.adSource = new SimpleStringProperty(adSource);
        this.adPosition = new SimpleStringProperty(adPosition);
        this.adRefNumb = new SimpleStringProperty(adRefNumb);
        //-------- Initialization End ------------\\
    }
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email.get();
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public String getFirstName() {
        return this.firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public String getMiddleName() {
        return this.middleName.get();
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }
    
    public String getLastName() {
        return this.lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public String getTitle() {
        return this.title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public String getCompany() {
        return this.company.get();
    }

    public void setCompany(String company) {
        this.company.set(company);
    }
    
    public String getAddress1() {
        return this.address1.get();
    }

    public void setAddress1(String address1) {
        this.address1.set(address1);
    }
    
    public String getAddress2() {
        return this.address2.get();
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }
    
    public String getCity() {
        return this.city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }
    
    public String getState() {
        return this.state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }
    
    public String getZip() {
        return this.zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }
    
    public String getAdSource() {
        return this.adSource.get();
    }

    public void setAdSource(String adSource) {
        this.adSource.set(adSource);
    }
    
    public String getAdPosition() {
        return this.adPosition.get();
    }

    public void setAdPosition(String adPosition) {
        this.adPosition.set(adPosition);
    }
    
    public String getAdRefNumb() {
        return this.adRefNumb.get();
    }

    public void setAdRefNumb(String adRefNumb) {
        this.adRefNumb.set(adRefNumb);
    }
}
