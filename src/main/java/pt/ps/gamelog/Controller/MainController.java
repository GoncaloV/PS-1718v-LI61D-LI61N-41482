package pt.ps.gamelog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.ps.gamelog.Model.Entities.*;
import pt.ps.gamelog.Model.Repos.PlayerRepository;

@Controller
@RequestMapping
public class MainController {
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(path="")
    public String getIndex(Model model) {
        model.addAttribute("player", new Player());
        return "index";
    }

    @PostMapping(path="/player")
    public String createPlayer(@ModelAttribute Player player){
        playerRepository.save(player);
        return "result";
    }
}