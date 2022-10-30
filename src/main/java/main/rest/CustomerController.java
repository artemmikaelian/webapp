package main.rest;

import main.Service.CustomerService;
import main.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger("default");
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

        LOGGER.info("Добавлен новый клиент.");
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
            LOGGER.info("Инфомрация о клиенте с ID=" + id + "не может быть обновлена, форма заполнена некорректно");
            return "edit";
        }
        customerService.save(customer);
        LOGGER.info("Информация о клиенте с ID=" + id + " была обновлена");
        return "edited_customer";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        if (customerService.findById(id).isPresent()) {
            LOGGER.info("Клиент с ID=" + id + " удален");
            customerService.delete(customerService.findById(id).get());
            return "redirect:/";
        } else {
            LOGGER.info("Удаление невозможно, клиента с таким ID нет");
            return "bad_request";
        }
    }

    @RequestMapping("/search_by_id")
    public String searchById(int id, Model model) {
        if (customerService.findById(id).isEmpty()) {
            LOGGER.info("Поиск по ID не дал результатов. Введен несуществующий ID");
            return "not_found";
        } else {
            LOGGER.info("Поиск по ID выполнен.");
            model.addAttribute("customer", customerService.findById(id).get());
            return "search_by_id";
        }
    }

    @RequestMapping("/search_by_fullname")
    public String searchByFullName(String surname, String name, String patronymic, Model model) {
        List<Customer> customers = customerService.findByFullName(surname, name, patronymic);
        LOGGER.info("Выполнен поиск по ФИО, клиентов найдено:" + customers.size());
        if (customers.isEmpty()) {
            return "not_found";
        }
        model.addAttribute("customers", customers);
        return "search_by_fullname";
    }
}
