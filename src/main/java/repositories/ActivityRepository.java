package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

}
