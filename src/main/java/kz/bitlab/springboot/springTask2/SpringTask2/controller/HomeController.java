package kz.bitlab.springboot.springTask2.SpringTask2.controller;

import kz.bitlab.springboot.springTask2.SpringTask2.model.RequestModel;
import kz.bitlab.springboot.springTask2.SpringTask2.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping(value = "/")
    public String homePage(Model model){
        List<RequestModel>requestModelList = requestRepository.findAll();
        model.addAttribute("requests",requestModelList);
        return "home";
    }

    @PostMapping(value = "/add-request")
    public String addRequest(RequestModel request){
        requestRepository.save(request);
        return "redirect:/";
    }

    @GetMapping(value = "/add-request")
    public String addRequestPage(Model model){
        return "addrequest";
    }

    @GetMapping(value = "/details/{requestId}")
    public String requestDetails(@PathVariable(name = "requestId") Long id, Model model){
        RequestModel request = requestRepository.findById(id).orElse(null);
        model.addAttribute("request", request);
        return "details";
    }

    @GetMapping(value = "/new-request")
    public String newRequest(Model model){
        List<RequestModel> requestModelList = requestRepository.findAllByHandledIsFalse();
        model.addAttribute("requests",requestModelList);
        return "home";
    }

    @GetMapping(value = "/seen-request")
    public String seenRequest(Model model){
        List<RequestModel> requestModelList = requestRepository.findAllByHandledIsTrue();
        model.addAttribute("requests",requestModelList);
        return "home";
    }

    @PostMapping(value = "/save-request")
    public String saveRequest(RequestModel request){
        request.setHandled(true);
        requestRepository.save(request);
        return "redirect:/";
    }

    @PostMapping(value = "/delete-request")
    public String deleteRequest(@RequestParam(name = "id") Long id){
        requestRepository.deleteById(id);
        return "redirect:/";
    }
}
