
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select p from Product p where p.isDraftMode = False and p.stock > 0")
	public List<Product> getProductsFinalModeWithStock();

	@Query("select p from Product p where p.isDraftMode = False")
	public List<Product> getProductsFinalMode();

	@Query("select p from SalesMan s join s.products p where p.isDraftMode = False and p.stock > 0 and s.id = ?1")
	public List<Product> getProductsFinalModeWithStockBySalesMan(int salesManId);

}
