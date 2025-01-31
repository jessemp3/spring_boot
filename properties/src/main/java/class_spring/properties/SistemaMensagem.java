package class_spring.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SistemaMensagem implements CommandLineRunner {
    @Autowired
    private Remetente remetente;
    // o application.properties é um arquivo semelhante ao .env do node

//    @Value("${name:NoReply-DIO}") //para colocar um valor padrão, usa o : e o valor
//    private String nome;
//    @Value("${email}")
//    private String email;
//    @Value("${telefones}")
//    private List<Long> telefones =
//            new ArrayList<>(Arrays.asList(new Long[]{11956781254L}));


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Mensagem enviada por: " + remetente.getNome()
                + "\nE-mail:" + remetente.getEmail()
                + "\nCom telefones para contato: " + remetente.getTelefones());
        System.out.println("Seu cadastro foi aprovado");
    }
}