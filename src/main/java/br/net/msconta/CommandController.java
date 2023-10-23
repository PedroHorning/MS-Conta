package br.net.msconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeCommand(@RequestBody Command command) {
        commandService.executeCommand(command);
        return ResponseEntity.ok("Command executed successfully.");
    }
}

@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/execute")
    public ResponseEntity<QueryResult> executeQuery(@RequestParam("query") String query) {
        QueryResult result = queryService.executeQuery(query);
        return ResponseEntity.ok(result);
    }
}

