package cn.rangers.develop.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("springboot")
public class HelloWorldController {

	@RequestMapping("/say")
	@ResponseBody
	public String sayHelloWorld() {
		return "hello world!!";
	}
	
	@RequestMapping("/who/{name}")
	@ResponseBody
	public String whoSayHelloWorld(@PathVariable("name")String name) {
		return  name + "-say : hello World!!";
	}
}
