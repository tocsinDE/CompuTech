/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kickstart.model.validation;

import org.hibernate.validator.constraints.NotEmpty;

public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.nickname.NotEmpty}")
	private String nickname;

	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	private String firstname;

	@NotEmpty(message = "{RegistrationForm.lastname.NotEmpty}")
	private String lastname;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;

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
