
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProductRepository;
import domain.Product;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository	productRepository;


	public List<Product> getProductsFinalModeWithStock() {
		return this.productRepository.getProductsFinalModeWithStock();
	}

	public List<Product> getProductsFinalMode() {
		return this.productRepository.getProductsFinalMode();
	}

	public List<Product> getProductsFinalModeWithStockBySalesMan(int salesManId) {
		return this.productRepository.getProductsFinalModeWithStockBySalesMan(salesManId);
	}

}
