package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAluno {

    @Autowired
    private AlunoRepository alunoRepository;

    public void save(Aluno aluno) {
        this.alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return this.alunoRepository.findAll();
    }

    public Aluno getById(Long id) {
        return this.alunoRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        this.alunoRepository.deleteById(id);
    }

    public List<Aluno> findByStatusAtivo() {
        return this.alunoRepository.findByStatusAtivo();
    }

    public List<Aluno> findByStatusInativo() {
        return this.alunoRepository.findByStatusInativo();
    }

    public List<Aluno> findByNomeContainingIgnoreCase(String nome) {
        return this.alunoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void delete(Long id) {
    }

}
