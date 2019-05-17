
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.ProductRepository;
import domain.Configuration;
import domain.Product;
import domain.SalesMan;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository		productRepository;

	@Autowired
	private SalesManService			salesManService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	public List<Product> getProductsFinalModeWithStock() {
		return this.productRepository.getProductsFinalModeWithStock();
	}

	public List<Product> getProductsFinalMode() {
		return this.productRepository.getProductsFinalMode();
	}

	public List<Product> getProductsFinalModeWithStockBySalesMan(int salesManId) {
		return this.productRepository.getProductsFinalModeWithStockBySalesMan(salesManId);
	}

	public Product create() {
		Product product = new Product();

		product.setDescription("");
		product.setIsDraftMode(true);
		product.setName("");
		product.setPrice(1);
		product.setStock(1);
		product.setType(null);

		return product;
	}

	public Product save(Product product) {
		return this.productRepository.save(product);
	}

	public Product findOne(int productId) {
		return this.productRepository.findOne(productId);
	}

	public Product reconstruct(Product product, BindingResult binding) {
		Product result = new Product();
		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		if (product.getId() == 0) {
			result = product;

		} else {
			Product copy = this.findOne(product.getId());

			if (copy.getIsDraftMode()) {

				result = product;
				result.setVersion(copy.getVersion());
				result.setId(copy.getId());

			} else {

				result = product;
				result.setDescription(copy.getDescription());
				result.setIsDraftMode(copy.getIsDraftMode());
				result.setName(copy.getName());
				result.setPrice(copy.getPrice());

				result.setType(copy.getType());
				result.setVersion(copy.getVersion());

				if (result.getStock() < copy.getStock()) {
					if (locale.contains("ES")) {
						binding.addError(new FieldError("product", "stock", product.getStock(), false, null, null, "La cantidad no puede ser menor a la anterior"));
					} else {
						binding.addError(new FieldError("product", "stock", product.getStock(), false, null, null, "Stock can not be less than before"));
					}
				}

			}

		}

		this.validator.validate(result, binding);
		return result;
	}
	public void addProduct(Product pro) {
		SalesMan salesman = this.salesManService.loggedSalesMan();
		Configuration configuration = this.configurationService.getConfiguration();
		Assert.isTrue(configuration.getTypeProducts().contains(pro.getType()) && pro.getId() == 0);
		Product product = this.save(pro);
		salesman.getProducts().add(product);
		this.salesManService.save(salesman);
	}
	public void updateProduct(Product pro) {
		SalesMan salesman = this.salesManService.loggedSalesMan();
		Configuration configuration = this.configurationService.getConfiguration();
		Assert.isTrue(configuration.getTypeProducts().contains(pro.getType()) && salesman.getProducts().contains(pro));
		this.save(pro);

	}

	public void delete(Product product) {
		SalesMan salesman = this.salesManService.loggedSalesMan();
		Assert.isTrue(product.getIsDraftMode() && salesman.getProducts().contains(product));
		salesman.getProducts().remove(product);
		this.salesManService.save(salesman);
		this.productRepository.delete(product);

	}

}
