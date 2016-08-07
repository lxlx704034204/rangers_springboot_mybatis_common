package cn.rangers.develop.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/page")
public class IndexController{

    @RequestMapping(value="{pageName}",method=RequestMethod.GET)
    public ModelAndView toPage(@PathVariable("pageName")String pageName){
    	System.out.println("当前页面："+pageName);
        return new ModelAndView(pageName);
    }
    
}
