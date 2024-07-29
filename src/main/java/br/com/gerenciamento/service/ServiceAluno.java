package br.com.gerenciamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

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
        return this.alunoRepository.findById(id).orElse(null);
    }

    public Optional<Aluno> getByMatricula(String matricula){
        return this.alunoRepository.getByMatricula(matricula);
    }

    public List<Aluno> findByCurso(Curso curso){
        return this.alunoRepository.findByCurso(curso);
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
}
