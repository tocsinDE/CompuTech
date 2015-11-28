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

package computech.model.validation;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.nickname.NotEmpty}")
	private String nickname;

	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	private String firstname;

	@NotEmpty(message = "{RegistrationForm.lastname.NotEmpty}")
	private String lastname;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;

	@Transient
	private String password2;

	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private String address;

	@NotEmpty(message = "{RegistrationForm.mail.NotEmpty}")
	private String mail;

	@NotEmpty(message = "{RegistrationForm.phone.NotEmpty}")
	private String phone;


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
}
