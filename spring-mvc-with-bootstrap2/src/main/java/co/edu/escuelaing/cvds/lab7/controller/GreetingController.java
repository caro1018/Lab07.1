package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.ToDoItem;
import co.edu.escuelaing.cvds.lab7.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/greeting")
public class GreetingController {
    private final ToDoService toDoService;

    @Autowired
    public GreetingController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/to-do-item/{id}")
    public String getToDoItem(@PathVariable Integer id, Model model) {
        ToDoItem toDoItem = toDoService.getItem(id);
        model.addAttribute("toDoItem", toDoItem);
        return "to-do-item";
    }
}
