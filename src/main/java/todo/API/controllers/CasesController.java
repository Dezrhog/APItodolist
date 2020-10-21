package todo.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todo.API.service.CasesServiceImpl;
import todo.API.service.ListsServiceImpl;

@RestController
@RequestMapping("lists/{id}/cases")
public class CasesController {

    private final CasesServiceImpl casesService;

    @Autowired
    private CasesController(CasesServiceImpl casesService) { this.casesService = casesService; }


}
