package com.example.finalproj.controllers;

import com.example.finalproj.enumm.Status;
import com.example.finalproj.models.*;
import com.example.finalproj.repositories.CategoryRepository;
import com.example.finalproj.repositories.OrderRepository;
import com.example.finalproj.repositories.PersonRepository;
import com.example.finalproj.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public AdminController(ProductService productService, CategoryRepository categoryRepository, OrderRepository orderRepository, PersonRepository personRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("admin/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }

        if (file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);

        }

        if (file_two != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if (file_three != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if (file_four != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if (file_five != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";


    }

    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    //    ТРИ МЕТОДА НИЖЕ - ВЕРНЫЕ
    @PostMapping("/admin/orders")
    public String showOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", Status.values());
        return "admin/orders";
    }

    @GetMapping("/ordersByNumber/{number}")
    public String showUserOrders(@PathVariable String number, Model model) {
        List<Order> orders = orderRepository.findByNumber(number);
        model.addAttribute("orders", orders);
        model.addAttribute("number", number);
        model.addAttribute("statuses", Status.values()); // добавляем список статусов
        return "admin/orders-by-number";
    }
//    @PostMapping("/admin/update-order-status")
//    public String updateOrderStatus(@RequestParam(name = "orderId") int id,
//                                    @RequestParam(name = "status") Status status) {
//        Order order = orderRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("Invalid order Id:" + id));
//        order.setStatus(status);
//        orderRepository.save(order);
//        return "redirect:/admin/orders"; // переадресация на список заказов
//    }

    // Для изменения статуса заказа, оставяясь на это же странице
    @RequestMapping(value = "/admin/update-order-status", method = RequestMethod.POST)
    public String updateOrderStatus(@RequestParam("orderId") int id,
                                    @RequestParam("status") Status status,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam(name = "page", defaultValue = "1") Integer page) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id:" + id));
        order.setStatus(status);
        orderRepository.save(order);
        redirectAttributes.addFlashAttribute("successMessage", "Статус заказа успешно изменен");
        return "redirect:/admin/orders?page=" + page;
    }

    @GetMapping("/admin/orders")
    public String showOrders(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Order> orders;

        if (search != null && search.length() >= 4) {
            String number = search.substring(search.length() - 4);
            orders = orderRepository.findByNumberContainingOrderByDateTimeDesc(number);
            model.addAttribute("search", search);
        } else {
            orders = orderRepository.findAllByOrderByDateTimeDesc();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("statuses", Status.values());
        return "admin/orders";
    }


    //Контроллеры для вывода пользователей и смены их ролей
//    @GetMapping("/admin/users")
//    public String getUsers(Model model) {
//        List<Person> users = personRepository.findAll();
//        model.addAttribute("users", users);
//        return "/admin/users";
//    }
//
//    // Метод изменения роли пользователя на admin
//    @PostMapping("/admin/user/makeadmin/{id}")
//    public String makeUserAdmin(@PathVariable("id") int id) {
//        Person user = personRepository.findById(id).orElseThrow();
//        user.setRole("ADMIN");
//        personRepository.save(user);
//        return "redirect:/admin/users";
//    }
//
//    @PostMapping("/admin/user/makeuser/{id}")
//    public String makeUserUser(@PathVariable("id") int id) {
//        Person user = personRepository.findById(id).orElseThrow();
//        user.setRole("USER");
//        personRepository.save(user);
//        return "redirect:/admin/users";
//    }
//    @GetMapping
//    public ModelAndView userList() {
//        List<Person> users = personRepository.findAll();
//        ModelAndView modelAndView = new ModelAndView("admin/users");
//        modelAndView.addObject("users", users);
//        return modelAndView;
//    }
//    @PostMapping("/change-role")
//    public String changeUserRole(@RequestParam int id, @RequestParam String newRole) {
//        Person user = personRepository.findById(id).orElse(null);
//        if (user != null) {
//            user.setRole(newRole);
//            personRepository.save(user);
//        }
//        return "redirect:/users";
//    }

    // НОРМ
//    @GetMapping("admin/users")
//    public String getAllUsers(Model model) {
//        List<Person> userList = personRepository.findAll();
//        model.addAttribute("users", userList);
//        return "admin/all_users";
//    }
    @PostMapping("/admin/users/update-role")
    public String updateUserRole(@RequestParam int id, @RequestParam String role) {
        // обновляем запись в базе данных
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setRole(role);
            personRepository.save(person);
        }

        // перенаправляем пользователя на страницу со списком пользователей
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        List<Person> people = personRepository.findAll();
        model.addAttribute("people", people);
        return "admin/users";
    }
}
