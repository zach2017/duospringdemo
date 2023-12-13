package zac.demo.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleRestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld() {
        return "Hello, World!";
    }
}
