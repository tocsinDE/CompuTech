/*
 *
 *	SWT-Praktikum TU Dresden 2015
 *	Gruppe 32 - Computech
 *
 *	Stephan Fischer
 *  Anna Gromykina
 *  Kevin Horst
 *  Philipp Oehme
 *
 */

package computech.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;


/**
 *
 * Basic class for Customer. The customer is based on Salespoint's "UserAccount" (which directly contains nickname and password).
 * Any other data of a customer (adress, name, mail, phone, connected employee for business customer) will be saved here.
 *
 */

@Entity
public class Customer {

    private @Id @GeneratedValue long id;

    private String address;
    private String firstname;
    private String lastname;

    private String mail;
    private String phone;

    @OneToOne private UserAccount connectedEmployee;

    @OneToOne(cascade=CascadeType.ALL) private UserAccount userAccount;

    @SuppressWarnings("unused")
    private Customer() {}

    public Customer(UserAccount userAccount, String address, String firstname, String lastname, String mail, String phone, UserAccount connectedEmployee) {
		this.userAccount = userAccount;
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.connectedEmployee = connectedEmployee;
    }



    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	public UserAccount getUserAccount() {
		return userAccount;
	}

    public UserAccount getConnectedEmployee() { return connectedEmployee; }

    public void setConnectedEmployee(UserAccount connectedEmployee) {this.connectedEmployee = connectedEmployee;}
}
