package sg.test.with.karate.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.test.with.karate.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
