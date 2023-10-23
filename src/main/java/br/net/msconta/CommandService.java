package br.net.msconta;

@Service
public class CommandService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CommandRepository commandRepository;

    @Value("${rabbitmq.command.exchange}")
    private String commandExchange;

    public void executeCommand(Command command) {
        // Lógica para executar o comando e salvar no banco de dados de comando
        commandRepository.save(command);
        rabbitTemplate.convertAndSend(commandExchange, "command." + command.getId(), command);
    }
}

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    public QueryResult executeQuery(String query) {
        // Lógica para executar a consulta no banco de dados de consulta
        return queryRepository.executeQuery(query);
    }
}
