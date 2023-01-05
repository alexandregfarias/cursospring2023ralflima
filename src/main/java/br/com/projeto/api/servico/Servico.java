package br.com.projeto.api.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto.api.modelo.Mensagem;
import br.com.projeto.api.modelo.Pessoa;
import br.com.projeto.api.repositorio.Repositorio;

@Service
public class Servico {

    @Autowired
    private Mensagem mensagem;

    @Autowired
    private Repositorio acao;

    // Método para cadastrar pessoas.
    public ResponseEntity<?> cadastrar(Pessoa obj) {
        if (obj.getNome().equals("")) {
            mensagem.setMensagem("O nome precisa ser preenchido");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (obj.getIdade() < 0) {
            mensagem.setMensagem("Informe uma idade válida.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {

            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    // Método para selecionar pessoas.
    public ResponseEntity<?> selecionar() {
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    // Método para selecionar pessoas através do código.
    public ResponseEntity<?> selecionarPeloCodigo(int codigo) {
        if (acao.countByCodigo(codigo) == 0) {
            mensagem.setMensagem("Não foi encontrada nenhuma pessoa.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(acao.findByCodigo(codigo), HttpStatus.OK);
        }
    }

    // Método para editar os dados
    public ResponseEntity<?> editar(Pessoa obj) {

        if (acao.countByCodigo(obj.getCodigo()) == 0) { // Quando o código não existir, o retorno é 0.
            mensagem.setMensagem("O código informado é inexistente."); // Então, quando for verdade que o código não
                                                                       // existe, entre no if.
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND); // É possível utilizar o badrequest também, mas
                                                                         // o not found é mais coerente visto o código
                                                                         // do objeto não foi encontrado.
        } else if (obj.getNome().equals("")) {
            mensagem.setMensagem("É necessário informar um nome.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST); // Neste caso se utliza o badrequest, pois
                                                                           // não tem o que buscar já que foi informado
                                                                           // uma String vazia.
        } else if (obj.getIdade() < 0) {
            mensagem.setMensagem("Informe uma idade válida.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
        }

    }

    // Método para remover registros
    public ResponseEntity<?> remover(int codigo) {

        if (acao.countByCodigo(codigo) == 0) {
            mensagem.setMensagem("O código informado não existe");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
           Pessoa obj = acao.findByCodigo(codigo);
            acao.delete(obj);

            mensagem.setMensagem("Pessoa removida com sucesso.");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }

    }

}
