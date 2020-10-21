package todo.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import todo.API.repository.ListsRepo;

@Controller
public class GreetingController {
    @Autowired
    private ListsRepo listsRepo;

    /*@GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }*/

    /*@GetMapping("/lists")
    @ResponseBody
    public Page<ListsEntity> findAll(
            @RequestParam Optional<String> title,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
            ) {
        return listsRepo.findByTitle(title.orElse("test"),
                PageRequest.of(
                        page.orElse(0), 5,
                Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }*/

    /*@PostMapping
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model) {
        Message message = new Message(text, tag);

        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "main";
    }*/

    /*@PostMapping("filter")
    public String filter(@RequestParam(required = false, defaultValue = "") String filter,
                         Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC)Pageable pageable
                         ) {
        Page<Message> page;

        if (filter != null && !filter.isEmpty()) {
            page = messageRepository.findByTag(filter, pageable);
        } else {
            page = messageRepository.findAll(pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("filter", filter);

        return "main";
    }*/
}
