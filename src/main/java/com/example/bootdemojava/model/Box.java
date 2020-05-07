package com.example.bootdemojava.model;

import java.util.ArrayList;
import java.util.List;

public class Box {

    // used as a key to the map, can't be changed
    public final String name;
    public final List<String> labels = new ArrayList<>();
    public final List<Item> items = new ArrayList<>();

    public Box(String name, String... labels) {
        this.name = name;
        this.labels.addAll(List.of(labels));
    }

    public void addItem(String name, String description, Double weightInKg) {
        Item item = new Item(name);
        item.description = description;
        item.weightInKg = weightInKg;
        items.add(item);
    }
}
