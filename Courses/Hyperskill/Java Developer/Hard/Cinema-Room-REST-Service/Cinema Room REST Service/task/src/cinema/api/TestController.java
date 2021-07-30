package cinema.api;

import java.util.*;
import cinema.model.TestModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    List<TestModel> taskList = List.of(
            new TestModel(1, "task1", "A first test task", false),
            new TestModel(2, "task2", "A second test task", true)
    );

    @GetMapping("/test")
    public List<TestModel> returnTasks() {
        return taskList;
    }

    @GetMapping("/test/{taskID}")
    public TestModel returnTasks(@PathVariable int taskID) {
        return taskList.get(taskID);
    }
}
