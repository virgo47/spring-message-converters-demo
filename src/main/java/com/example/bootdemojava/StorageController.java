package com.example.bootdemojava;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;

import com.example.bootdemojava.model.Box;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "/storage",
        produces = { MediaType.ALL_VALUE }
)
public class StorageController {

    private final Map<String, Box> boxes = new TreeMap<>();

    @PostConstruct
    public void init() {
        Box cycling = new Box("cycling");
        cycling.addItem("bottle", "yellow bottle", 0.3);
        cycling.addItem("pump", "pump, two vent sizes", 0.7);
        addBox(cycling);

        Box recording = new Box("recording");
        recording.addItem("mixer1", "some Behringer I guess", 1.5);
        recording.addItem("mic1", "dynamic microphone", 0.47);
        addBox(recording);
    }

    @GetMapping
    public Collection<Box> listBoxes() {
        return boxes.values();
    }

    private void addBox(Box box) {
        boxes.put(box.name, box);
    }
}
