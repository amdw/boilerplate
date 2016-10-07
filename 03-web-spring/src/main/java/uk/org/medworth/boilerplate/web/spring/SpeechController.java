package uk.org.medworth.boilerplate.web.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpeechController {
    @RequestMapping("/speech")
    public String speech(
            @RequestParam(value = "occasion", required = false, defaultValue = "Hong Kong Code Conf") String occasion,
            Model model
    ) {
        model.addAttribute("occasion", occasion);
        return "speech";
    }
}
