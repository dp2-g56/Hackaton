
package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import services.GuardService;
import services.SalesManService;
import services.VisitorService;
import services.WardenService;
import domain.Guard;
import domain.Product;
import domain.SalesMan;
import domain.Visitor;
import domain.Warden;

@Controller
@RequestMapping("/export")
public class ExportDataController {

	@Autowired
	public WardenService	wardenService;
	@Autowired
	public GuardService		guardService;
	@Autowired
	public SalesManService	salesManService;
	@Autowired
	public VisitorService	visitorService;


	@RequestMapping(value = "/warden", method = RequestMethod.GET)
	public @ResponseBody
	String export(@RequestParam(value = "id", defaultValue = "-1") int id, HttpServletResponse response) throws IOException {

		this.wardenService.loggedAsWarden();

		Warden warden = new Warden();
		warden = this.wardenService.findOne(id);

		// Defines un StringBuilder para construir tu string
		StringBuilder sb = new StringBuilder();

		// linea
		sb.append("Personal data:").append(System.getProperty("line.separator"));
		sb.append("- Name: " + warden.getName()).append(System.getProperty("line.separator"));
		sb.append("- Middle name: " + warden.getMiddleName()).append(System.getProperty("line.separator"));
		sb.append("- Surname: " + warden.getSurname()).append(System.getProperty("line.separator"));
		sb.append("- Photo: " + warden.getPhoto()).append(System.getProperty("line.separator"));

		// Defines el nombre del archivo y la extension
		response.setContentType("text/txt");
		response.setHeader("Content-Disposition", "attachment;filename=exportDataWarden.txt");

		// Con estos comandos permites su descarga cuando clickas
		ServletOutputStream outStream = response.getOutputStream();
		outStream.println(sb.toString());
		outStream.flush();
		outStream.close();

		// El return no llega nunca, es del metodo viejo
		return sb.toString();
	}

	@RequestMapping(value = "/guard", method = RequestMethod.GET)
	public @ResponseBody
	String exportGuard(@RequestParam(value = "id", defaultValue = "-1") int id, HttpServletResponse response) throws IOException {

		this.guardService.loggedAsGuard();

		Guard guard = new Guard();
		guard = this.guardService.findOne(id);

		// Defines un StringBuilder para construir tu string
		StringBuilder sb = new StringBuilder();

		// linea
		sb.append("Personal data:").append(System.getProperty("line.separator"));
		sb.append("- Name: " + guard.getName()).append(System.getProperty("line.separator"));
		sb.append("- Middle name: " + guard.getMiddleName()).append(System.getProperty("line.separator"));
		sb.append("- Surname: " + guard.getSurname()).append(System.getProperty("line.separator"));
		sb.append("- Photo: " + guard.getPhoto()).append(System.getProperty("line.separator"));

		sb.append("- Phone: " + guard.getPhone()).append(System.getProperty("line.separator"));
		sb.append("- Email: " + guard.getEmail()).append(System.getProperty("line.separator"));

		// Defines el nombre del archivo y la extension
		response.setContentType("text/txt");
		response.setHeader("Content-Disposition", "attachment;filename=exportDataWarden.txt");

		// Con estos comandos permites su descarga cuando clickas
		ServletOutputStream outStream = response.getOutputStream();
		outStream.println(sb.toString());
		outStream.flush();
		outStream.close();

		// El return no llega nunca, es del metodo viejo
		return sb.toString();
	}

	@RequestMapping(value = "/salesman", method = RequestMethod.GET)
	public @ResponseBody
	String exportSalesman(@RequestParam(value = "id", defaultValue = "-1") int id, HttpServletResponse response) throws IOException {

		this.salesManService.loggedAsSalesMan();

		SalesMan salesman = new SalesMan();
		salesman = this.salesManService.findOne(id);

		// Defines un StringBuilder para construir tu string
		StringBuilder sb = new StringBuilder();

		// linea
		sb.append("Personal data:").append(System.getProperty("line.separator"));
		sb.append("- Name: " + salesman.getName()).append(System.getProperty("line.separator"));
		sb.append("- Middle name: " + salesman.getMiddleName()).append(System.getProperty("line.separator"));
		sb.append("- Surname: " + salesman.getSurname()).append(System.getProperty("line.separator"));
		sb.append("- Photo: " + salesman.getPhoto()).append(System.getProperty("line.separator"));
		sb.append("- VAT Number: " + salesman.getVATNumber()).append(System.getProperty("line.separator"));
		sb.append("- Store name: " + salesman.getStoreName()).append(System.getProperty("line.separator"));
		sb.append("- Points: " + salesman.getPoints()).append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));

		List<Product> products = salesman.getProducts();
		sb.append("List of Products").append(System.getProperty("line.separator"));
		sb.append("---").append(System.getProperty("line.separator"));
		for (Product p : products) {
			sb.append("Product '" + p.getName() + "':").append(System.getProperty("line.separator"));
			sb.append("- Description: " + p.getDescription()).append(System.getProperty("line.separator"));
			sb.append("- Price: " + p.getPrice()).append(System.getProperty("line.separator"));
			sb.append("- Stock: " + p.getStock()).append(System.getProperty("line.separator"));
			sb.append("- ¿Is draft mode?: " + p.getIsDraftMode()).append(System.getProperty("line.separator"));
			sb.append(System.getProperty("line.separator"));
		}

		// Defines el nombre del archivo y la extension
		response.setContentType("text/txt");
		response.setHeader("Content-Disposition", "attachment;filename=exportDataSalesman.txt");

		// Con estos comandos permites su descarga cuando clickas
		ServletOutputStream outStream = response.getOutputStream();
		outStream.println(sb.toString());
		outStream.flush();
		outStream.close();

		// El return no llega nunca, es del metodo viejo
		return sb.toString();
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.GET)
	public @ResponseBody
	String exportVisitor(@RequestParam(value = "id", defaultValue = "-1") int id, HttpServletResponse response) throws IOException {

		this.visitorService.loggedAsVisitor();

		Visitor visitor = new Visitor();
		visitor = this.visitorService.findOne(id);

		// Defines un StringBuilder para construir tu string
		StringBuilder sb = new StringBuilder();

		// linea
		sb.append("Personal data:").append(System.getProperty("line.separator"));
		sb.append("- Name: " + visitor.getName()).append(System.getProperty("line.separator"));
		sb.append("- Middle name: " + visitor.getMiddleName()).append(System.getProperty("line.separator"));
		sb.append("- Surname: " + visitor.getSurname()).append(System.getProperty("line.separator"));
		sb.append("- Photo: " + visitor.getPhoto()).append(System.getProperty("line.separator"));
		sb.append("- Email: " + visitor.getEmail()).append(System.getProperty("line.separator"));
		sb.append("- Emergency email: " + visitor.getEmergencyEmail()).append(System.getProperty("line.separator"));
		sb.append("- Phone: " + visitor.getPhoneNumber()).append(System.getProperty("line.separator"));
		sb.append("- Address: " + visitor.getAddress()).append(System.getProperty("line.separator"));

		// Defines el nombre del archivo y la extension
		response.setContentType("text/txt");
		response.setHeader("Content-Disposition", "attachment;filename=exportDataVisitor.txt");

		// Con estos comandos permites su descarga cuando clickas
		ServletOutputStream outStream = response.getOutputStream();
		outStream.println(sb.toString());
		outStream.flush();
		outStream.close();

		// El return no llega nunca, es del metodo viejo
		return sb.toString();
	}

}
