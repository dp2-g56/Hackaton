
package controllers;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import services.GuardService;
import services.WardenService;
import domain.Guard;
import domain.Warden;

@Controller
@RequestMapping("/export")
public class ExportDataController {

	@Autowired
	public WardenService	wardenService;

	@Autowired
	public GuardService		guardService;


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

}
