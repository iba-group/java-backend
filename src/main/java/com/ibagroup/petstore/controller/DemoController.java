package com.ibagroup.petstore.controller;

import com.ibagroup.petstore.model.TestMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DemoController {

    public List<TestMessage> list = new ArrayList<>();
    public long id = 2;

    public DemoController() {
        this.list.add(new TestMessage(1, "Message text", "Alex"));
    }

    @GetMapping("/all-messages")
    public List<TestMessage> getSimpleMessage(@RequestParam(required = false) Long id) {
        return id != null
                ? list.stream().filter(item -> item.getId() < id).collect(Collectors.toList())
                : list;
    }

//    @PostMapping("/add")
//    public TestMessage createMessage(@RequestParam String text, @RequestParam String author) {
//        TestMessage message = new TestMessage(id++, text, author);
//        list.add(message);
//        return message;
//    }

    @PostMapping("/add")
    public TestMessage createMessage(@RequestBody TestMessage inputMessage) {
        TestMessage message = new TestMessage(id++, inputMessage.getText(), inputMessage.getAuthor());
        list.add(message);
        return message;
    }

    @PostMapping("/remove/{id}")
    public TestMessage removeMessage(@PathVariable("id") Long requestedId) {
        TestMessage message = list.stream().filter(item -> item.getId() == requestedId).findFirst().get();
        list.remove(message);
        return message;
    }

    @PostMapping("/update/{id}")
    public TestMessage updateMessage(@PathVariable("id") Long requestedId, @RequestBody TestMessage inputMessage) {
        TestMessage message = list.stream().filter(item -> item.getId() == requestedId).findFirst().get();
        message.setAuthor(inputMessage.getAuthor());
        message.setText(inputMessage.getText());
        return message;
    }
}
