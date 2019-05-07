package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Prisoner;

public interface PrisonerRepository extends JpaRepository<Prisoner, Integer> {

}
