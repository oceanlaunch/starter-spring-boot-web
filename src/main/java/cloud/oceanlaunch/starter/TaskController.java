package cloud.oceanlaunch.starter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/** Server-rendered pages: a task list with a form. The page is src/main/resources/templates/index.html. */
@Controller
class TaskController {

    record Task(long id, String title, boolean done) {
    }

    /** What the form sends. Validation messages show next to the field. */
    static class TaskForm {
        @NotBlank(message = "Write what needs doing.")
        @Size(max = 120, message = "Keep it under 120 characters.")
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private final AtomicLong ids = new AtomicLong();
    // Kept in memory, so the list starts empty after each deploy. Add a database to keep it.
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("form", new TaskForm());
        return page(model);
    }

    @PostMapping("/tasks")
    String add(@Valid @ModelAttribute("form") TaskForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return page(model);
        }
        tasks.add(new Task(ids.incrementAndGet(), form.getTitle().strip(), false));
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/toggle")
    String toggle(@PathVariable long id) {
        tasks.replaceAll(t -> t.id() == id ? new Task(t.id(), t.title(), !t.done()) : t);
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/delete")
    String delete(@PathVariable long id) {
        tasks.removeIf(t -> t.id() == id);
        return "redirect:/";
    }

    private String page(Model model) {
        model.addAttribute("tasks", tasks);
        model.addAttribute("open", tasks.stream().filter(t -> !t.done()).count());
        return "index";
    }
}
