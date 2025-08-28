package sg.test.with.karate.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.test.with.karate.demo.CustomerRepository;
import sg.test.with.karate.demo.dto.CustomerDto;
import sg.test.with.karate.demo.dto.CustomerRequestDto;
import sg.test.with.karate.demo.model.Customer;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CREATE: POST /api/customers
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerRequestDto req) {
        Customer customer = new Customer();
        customer.setName(req.getName());
        customer.setEmail(req.getEmail());
        customer = customerRepository.save(customer);

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // READ: GET /api/customers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Customer c = opt.get();
        CustomerDto dto = new CustomerDto();
        dto.setId(c.getId());                // <-- bug fixed (was dto.getId())
        dto.setName(c.getName());
        return ResponseEntity.ok(dto);
    }

    // UPDATE: PUT /api/customers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
                                                      @RequestBody CustomerRequestDto req) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Customer c = opt.get();
        c.setName(req.getName());
        c.setEmail(req.getEmail());
        c = customerRepository.save(c);

        CustomerDto dto = new CustomerDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        return ResponseEntity.ok(dto);
    }

    // DELETE: DELETE /api/customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
