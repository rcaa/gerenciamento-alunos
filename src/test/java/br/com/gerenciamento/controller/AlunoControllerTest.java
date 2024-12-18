package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private AlunoRepository alunoRepository;

    // Configuração do ambiente de teste: criando um aluno de exemplo
    @BeforeEach
    public void setUp() {
        alunoRepository.deleteAll(); // Limpa a base de dados antes de cada teste
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Teste");
        aluno.setMatricula("12345");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        serviceAluno.save(aluno);
    }

    // Teste de integração para verificar se a página de cadastro de alunos está acessível
    @Test
    public void testInsertAlunoPage() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera-se que a página carregue com sucesso
                .andExpect(MockMvcResultMatchers.view().name("Aluno/formAluno")); // Verifica se a view correta é carregada
    }

    // Teste de integração para verificar a inserção de um aluno
    @Test
    public void testInsertAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                .param("nome", "Novo Aluno")
                .param("matricula", "67890")
                .param("curso", Curso.ADMINISTRACAO.name())
                .param("status", Status.ATIVO.name())
                .param("turno", Turno.NOTURNO.name()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Verifica se ocorre um redirecionamento
                .andExpect(MockMvcResultMatchers.redirectedUrl("/alunos-adicionados")); // Verifica o URL do redirecionamento
    }

    // Teste de integração para verificar se a listagem de alunos está funcionando corretamente
    @Test
    public void testListAlunosPage() throws Exception {
        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera-se que a página carregue com sucesso
                .andExpect(MockMvcResultMatchers.view().name("Aluno/listAlunos")) // Verifica se a view correta é carregada
                .andExpect(MockMvcResultMatchers.model().attributeExists("alunosList")); // Verifica se a lista de alunos foi adicionada ao modelo
    }

    // Teste de integração para verificar a remoção de um aluno
    @Test
    public void testRemoveAluno() throws Exception {
        Aluno aluno = alunoRepository.findAll().get(0); // Recupera o primeiro aluno inserido no banco
        Long alunoId = aluno.getId();

        mockMvc.perform(get("/remover/" + alunoId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Verifica o redirecionamento após a remoção
                .andExpect(MockMvcResultMatchers.redirectedUrl("/alunos-adicionados")); // Verifica o redirecionamento para a listagem de alunos
    }
}
