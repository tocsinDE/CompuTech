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

package computech.controller;


	import javax.validation.Valid;

	import computech.model.validation.profileEditForm;
	import org.salespointframework.useraccount.Role;
	import org.salespointframework.useraccount.UserAccount;
	import org.salespointframework.useraccount.UserAccountIdentifier;
	import org.salespointframework.useraccount.UserAccountManager;
	import org.salespointframework.useraccount.web.LoggedIn;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.access.prepost.PreAuthorize;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.ui.ModelMap;
	import org.springframework.util.Assert;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;

	import computech.model.Customer;
	import computech.model.CustomerRepository;
	import computech.model.validation.RegistrationForm;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;

	import java.util.Optional;

/**
 *
 * Contains basic shop features: registration (of private and business customers), form for customer for updating own data and several pages (index, about us, login)
 *
 */

@Controller
	public class ShopController {

		private final UserAccountManager userAccountManager;
		private final CustomerRepository customerRepository;

		@Autowired
		public ShopController(UserAccountManager userAccountManager, CustomerRepository customerRepository) {

			Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
			Assert.notNull(customerRepository, "CustomerRepository must not be null!");

			this.userAccountManager = userAccountManager;
			this.customerRepository = customerRepository;
		}

    	/**
		 *
	 	* Shows the index page.
		 *
	 	* @return template "index"
	 	*/
		@RequestMapping({ "/", "/index" })
		public String index() {
			return "index";
		}

		/**
		 *
		 * Shows the login form.
		 *
		 * @return template "login"
		 */
		@RequestMapping("/login")
		public String login() {return "login";}


		/**
		 *
		 * Shows the "about us" page.
		 *
		 * @return template "compu"
		 */
		@RequestMapping("/comp")
		public String aboutus() {return "compu";}


		// diese Methode wird sowohl für Geschäfts- als auch für Privatkunden verwendet
		// standardmäßig können sich Privatkunden hiermit selbst registrieren
		// der Boss kann hiermit Geschäftskunden anlegen


		/**
		 *
		 * Saving a new customer.
		 *
		 * @param registrationForm form that needs to be validated
		 * @param result validation of the form
		 * @param modelMap for creating a business customer: contains a list of all employees
		 * @param success notification about adding a new customer
		 * @return redirect to template "login" (if new private customer is created), to template "customers" (if new business customer is created) (or back to the form when there are input errors to fix)
		 */
		@RequestMapping("/registerNew")
		public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm, BindingResult result, ModelMap modelMap, RedirectAttributes success) {

			modelMap.addAttribute("employeeList_enabled", userAccountManager.findEnabled());

			if (result.hasErrors()) {
				return "register";
			}


			UserAccount userAccount = userAccountManager.create(registrationForm.getNickname(), registrationForm.getPassword(), Role.of(registrationForm.getRole()));
			userAccountManager.save(userAccount);

			if(registrationForm.getRole().equals("ROLE_PCUSTOMER")) {
				// normale Registrierung für Privatkunden
				Customer customer = new Customer(userAccount, registrationForm.getAddress(), registrationForm.getFirstname(), registrationForm.getLastname(), registrationForm.getMail(), registrationForm.getPhone(), null);
				customerRepository.save(customer);
			} else {
				// Registrierung eines Geschäftskunden
				UserAccount employee = userAccountManager.findByUsername(registrationForm.getConnectedEmployee()).get();
				Customer customer = new Customer(userAccount, registrationForm.getAddress(), registrationForm.getFirstname(), registrationForm.getLastname(), registrationForm.getMail(), registrationForm.getPhone(), employee);
				customerRepository.save(customer);
			}

			if(registrationForm.getRole().equals("ROLE_PCUSTOMER")) {
				success.addFlashAttribute("success", "Sie haben sich erfolgreich registriert und können sich mit Ihren Zugangsdaten einloggen.");
				return "redirect:/login";
			} else {
				success.addFlashAttribute("success", "Der Geschäftskunde wurde erfolgreich registriert.");
				return "redirect:/customers";
			}
		}

		/**
		 *
		 * Shows registration form.
		 *
		 * @param modelMap contains the concrete form for registration and, for business customers, a list of all employees
		 * @return template "register"
		 */
		@RequestMapping("/register")
		public String register(ModelMap modelMap) {
			modelMap.addAttribute("employeeList_enabled", userAccountManager.findEnabled());
			modelMap.addAttribute("registrationForm", new RegistrationForm());
			return "register";
		}


		/**
		 *
		 * Shows the data update form.
		 *
		 * @param model contains the currently logged in user
		 * @param modelMap contains the concrete data update form
		 * @param userAccount currently logged in user
		 * @return template "profile"
		 */
		@PreAuthorize("hasAnyRole('ROLE_PCUSTOMER', 'ROLE_BCUSTOMER')")
		@RequestMapping("/profile")
		public String editProfile(Model model, ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount) {
			modelMap.addAttribute("profileEditForm", new profileEditForm());

			Customer customer = customerRepository.findByUserAccount(userAccount.get());

			model.addAttribute("customer", customer);
			return "profile";
		}


		/**
		 *
		 * Validates the data update form and saves the new customer data.
		 *
		 * @param model contains the currently logged in user
		 * @param profileEditForm form that needs to be validated
		 * @param result validation of the form
		 * @param userAccount currently logged in user
		 * @param success notification about updating customer data
		 * @return redirect to template "profile" (with error messages when there are input errors to fix)
		 */
		@PreAuthorize("hasAnyRole('ROLE_PCUSTOMER', 'ROLE_BCUSTOMER')")
		@RequestMapping(value = "/profile", method = RequestMethod.POST)
		public String saveProfile(Model model, @ModelAttribute("profileEditForm") @Valid profileEditForm profileEditForm, BindingResult result, @LoggedIn Optional<UserAccount> userAccount, RedirectAttributes success) {
			Customer customer = customerRepository.findByUserAccount(userAccount.get());
			model.addAttribute("customer", customer);

			if (result.hasErrors()) {
				return "profile";
			}

			customer.setFirstname(profileEditForm.getFirstname());
			customer.setLastname(profileEditForm.getLastname());
			customer.setMail(profileEditForm.getMail());
			customer.setPhone(profileEditForm.getPhone());
			customer.setAddress(profileEditForm.getAddress());

			customerRepository.save(customer);

			if(profileEditForm.getPassword() != "") {
				userAccountManager.changePassword(customer.getUserAccount(), profileEditForm.getPassword());
			}


			success.addFlashAttribute("success", "Ihre Daten wurden erfolgreich geändert.");
			return "redirect:/profile";
		}


}

