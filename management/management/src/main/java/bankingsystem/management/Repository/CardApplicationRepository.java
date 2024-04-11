package bankingsystem.management.Repository;

import bankingsystem.management.Models.CardApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardApplicationRepository extends JpaRepository<CardApplication, Long> {

}
