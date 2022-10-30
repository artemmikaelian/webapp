package main.rest;

import main.Service.CustomerService;
import main.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/")
    public String mainPage(Model model) {
        List<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers);
        model.addAttribute("customersCount", customers.size());
        return "main_page";
    }

    @GetMapping("/new")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "add_customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "add_customer";
        }
        customerService.save(customer);
        return "new_customer";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam int id, Model model) {
        if (customerService.findById(id).isPresent()) {
            model.addAttribute("customer", customerService.findById(id).get());
            return "edit";
        } else {
            return "bad_request";
        }
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam int id, @Valid Customer customer, BindingResult result) {
            if (result.hasErrors()) {
                return "edit";
            }
            customerService.save(customer);
            return "edited_customer";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        if (customerService.findById(id).isPresent()) {
            customerService.delete(customerService.findById(id).get());
            return "redirect:/";
        } else {
            return "bad_request";
        }
    }

    @RequestMapping("/search_by_id")
    public String searchById(int id, Model model) {
        if (customerService.findById(id).isPresent()) {
            model.addAttribute("customer", customerService.findById(id).get());
            return "search_by_id";
        } else {
            return "not_found";
        }
    }

    @RequestMapping("/search_by_fullname")
    public String searchByFullName(String surname, String name, String patronymic, Model model) {
        List<Customer> customers = customerService.findByFullName(surname, name, patronymic);
        if (customers.isEmpty()) {
            return "not_found";
        }
        model.addAttribute("customers", customers);
        return "search_by_fullname";
    }
}
