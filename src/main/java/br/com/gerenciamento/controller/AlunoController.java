package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import jakarta.validation.Valid;

@Controller
public class AlunoController {

    @Autowired
    private ServiceAluno serviceAluno;

    @GetMapping("/inserirAlunos")
    public ModelAndView insertAlunos() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Aluno/formAluno");
        modelAndView.addObject("aluno", new Aluno());
        return modelAndView;
    }

    @PostMapping("InsertAlunos")
    public ModelAndView inserirAluno(@Valid Aluno aluno, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Aluno/formAluno");
            modelAndView.addObject("aluno");
        } else {
            modelAndView.setViewName("redirect:/alunos-adicionados");
            serviceAluno.save(aluno);
        }
        return modelAndView;
    }

    @GetMapping("alunos-adicionados")
    public ModelAndView listagemAlunos() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Aluno/listAlunos");
        modelAndView.addObject("alunosList", serviceAluno.findAll());
        return modelAndView;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id")Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Aluno/editar");
        Aluno aluno = serviceAluno.getById(id);
        modelAndView.addObject("aluno", aluno);
        return modelAndView;
    }

    @PostMapping("/editar")
    public ModelAndView editar(@RequestBody Aluno aluno) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        serviceAluno.save(aluno);
        modelAndView.setViewName("redirect:/alunos-adicionados");
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable("id") Long id) {
        serviceAluno.deleteById(id);
        return "redirect:/alunos-adicionados";
    }

    @GetMapping("filtro-alunos")
    public ModelAndView filtroAlunos() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Aluno/filtroAlunos");
        return modelAndView;
    }

    @GetMapping("alunos-ativos")
    public ModelAndView listaAlunosAtivos() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Aluno/alunos-ativos");
        modelAndView.addObject("alunosAtivos", serviceAluno.findByStatusAtivo());
        return modelAndView;
    }

    @GetMapping("alunos-inativos")
    public ModelAndView listaAlunosInativos() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Aluno/alunos-inativos");
        modelAndView.addObject("alunosInativos", serviceAluno.findByStatusInativo());
        return modelAndView;
    }

    @PostMapping("/pesquisar-aluno")
    public ModelAndView pesquisarAluno(@RequestParam(required = false) String nome) {
        ModelAndView modelAndView = new ModelAndView();
        List<Aluno> listaAlunos;
        if (nome == null || nome.trim().isEmpty()) {
            listaAlunos = serviceAluno.findAll();
        } else {
            listaAlunos = serviceAluno.findByNomeContainingIgnoreCase(nome);
        }
        modelAndView.addObject("ListaDeAlunos", listaAlunos);
        modelAndView.setViewName("Aluno/pesquisa-resultado");
        return modelAndView;
    }
}
