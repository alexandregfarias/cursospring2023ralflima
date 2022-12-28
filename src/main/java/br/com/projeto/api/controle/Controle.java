package br.com.projeto.api.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.modelo.Pessoa;
import br.com.projeto.api.repositorio.Repositorio;

@RestController
public class Controle {
 
  // O objeto Repositorio obtem as ações do banco de dados:
  // Cadastrar, Selecionar, Alterar e Excluir.
  @Autowired // Garantirá instanciação do objeto ao executar o Spring
  private Repositorio acao;

  @PostMapping("/api")
  public Pessoa cadastrar(@RequestBody Pessoa obj) {
    return acao.save(obj);
  }


  @GetMapping("/boasvindas")
    public String boasVindas() {
        return "Seja bem vindo(a).";
    }

    @GetMapping("/boasvindas/{nome}")
    public String boasVindas(@PathVariable String nome) {
        return "Seja bem vindo(a) " + nome;
    }

    @PostMapping("/pessoa")
    public Pessoa pessoa(@RequestBody Pessoa p) {
      return p;
    }
    
}
