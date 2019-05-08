
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SalesMan;

@Repository
public interface SalesManRepository extends JpaRepository<SalesMan, Integer> {

}
