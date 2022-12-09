package com.example.demo.services;
import com.example.demo.DTO.Request.CustomerRequest;
import com.example.demo.DTO.Response.CustomerResponse;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartService cartService;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartService cartService) {
        this.customerRepository = customerRepository;
        this.cartService = cartService;
    }
    public CustomerResponse getCustomer(Long customerId) {
        boolean exist=customerRepository.existsById(customerId);
        if (!exist)
            throw new IllegalStateException("Customer with id "+customerId+" doesn't exist");

        CustomerResponse response = new CustomerResponse();
        Customer customer = customerRepository.findById(customerId).get();
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        return response;
    }
    public void postCustomer(CustomerRequest request){
        Optional<Customer> customerOptional=customerRepository
                .findCustomerByEmail(request.getEmail());
        if(customerOptional.isPresent())
            throw new IllegalStateException("This email is taken");

        Customer customer= new Customer();
        cartService.createCartForCustomer(customer);
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customerRepository.save(customer);
    }
    public void deleteCustomer(Long customerId) {
        boolean exist=customerRepository.existsById(customerId);
        if(!exist)
            throw new IllegalStateException("Customer with id "+customerId+" doesn't exist");
        customerRepository.deleteById(customerId);
    }

    public void updateCustomer(Long customerId, CustomerRequest request) {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()-> new IllegalStateException(
                        "Customer with id "+customerId+" doesn't exist"
                ));
        if(request.getName()!=null &&
                request.getName().length()>0 &&
                !Objects.equals(customer.getName(),request.getName())){
            customer.setName(request.getName());
        }
        if(request.getEmail()!=null &&
            request.getEmail().length()>0&&
                !Objects.equals(customer.getEmail(),request.getEmail())){
            customer.setEmail(request.getEmail());
        }
        if(request.getPassword()!=null && request.getPassword().length()>0){
            customer.setPassword(request.getPassword());
        }
        customerRepository.save(customer);
    }
}
