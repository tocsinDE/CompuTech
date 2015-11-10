package kickstart.controller;

import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.time.BusinessTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kickstart.model.Comment;
import kickstart.model.Computer;
import kickstart.model.Computer.ComputerType;
import kickstart.model.ComputerCatalog;

@Controller
class CatalogController {

	private final ComputerCatalog videoCatalog;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime businessTime;

	// (｡◕‿◕｡)
	// Da wir nur ein Catalog.html-Template nutzen, aber dennoch den richtigen Titel auf der Seite haben wollen,
	// nutzen wir den MessageSourceAccessor um an die messsages.properties Werte zu kommen
	private final MessageSourceAccessor messageSourceAccessor;

	@Autowired
	public CatalogController(ComputerCatalog videoCatalog, Inventory<InventoryItem> inventory, BusinessTime businessTime,
			MessageSource messageSource) {

		this.videoCatalog = videoCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	@RequestMapping("/computerCatalog")
	public String computerCatalog(ModelMap modelMap) {

		modelMap.addAttribute("catalog", videoCatalog.findByType(ComputerType.COMPUTER));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.COMPUTER.title"));

		return "computerCatalog";
	}

	@RequestMapping("/notebookCatalog")
	public String notebookCatalog(Model model) {

		model.addAttribute("catalog", videoCatalog.findByType(ComputerType.NOTEBOOK));
		model.addAttribute("title", messageSourceAccessor.getMessage("catalog.bluray.title"));

		return "notebookCatalog";
	}

	// (｡◕‿◕｡)
	// Befindet sich die angesurfte Url in der Form /foo/5 statt /foo?bar=5 so muss man @PathVariable benutzen
	// Lektüre: http://spring.io/blog/2009/03/08/rest-in-spring-3-mvc/
	@RequestMapping("/detail/{pid}")
	public String detail(@PathVariable("pid")  , Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(disc.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);

		model.addAttribute("disc", disc);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));

		return "detail";
	}

	// (｡◕‿◕｡)
	// Der Katalog bzw die Datenbank "weiß" nicht, dass die Disc mit einem Kommentar versehen wurde,
	// deswegen wird die update-Methode aufgerufen
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("pid") Disc disc, @RequestParam("comment") String comment,
			@RequestParam("rating") int rating) {

		disc.addComment(new Comment(comment, rating, businessTime.getTime()));
		videoCatalog.save(disc);

		return "redirect:detail/" + disc.getIdentifier();
	}
}
