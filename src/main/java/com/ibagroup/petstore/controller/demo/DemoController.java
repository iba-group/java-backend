package com.ibagroup.petstore.controller.demo;

import com.ibagroup.petstore.dto.demo.TestMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
        this.list.add(new TestMessage(1, "TestMessage text", "Alex"));
    }

    @GetMapping("/all-messages")
    @ApiOperation(value = "Returns list of messages",
        notes = "Some notes (f.e. database tables which are used behind)",
        response = TestMessage.class)
    public List<TestMessage> getSimpleMessage(
        @ApiParam(value = "Id of the message",
            required = true,
            example = "1")
        @RequestParam Long id) {
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
