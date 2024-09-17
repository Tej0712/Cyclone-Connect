package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */


public class Person {
    private static long idCounter = 0;

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;

    private long id;  // Use UUID for a unique identifier

    private String email;



    public Person(){
        this.id = getNextId();
    }

    public Person(String firstName, String lastName, String address, String telephone, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }

    public void setEmail(String email) {
        if(email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private static synchronized long getNextId() {
        return ++idCounter;
    }

    // Email getter
    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + firstName + " " + lastName + ", Address: " + address + ", Telephone: " + telephone + ", Email: " + email;
    }
}
