package br.com.gerenciamento.controller;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void inserirAluno() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(112L);
        aluno.setNome("Italo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("111111");

        int qtdAluno1 = serviceAluno.findAll().size();
    
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        alunoController.inserirAluno(aluno, bindingResult);
        int qtdAluno2 = serviceAluno.findAll().size();

        assertEquals(qtdAluno1 + 1, qtdAluno2); 
        List<Aluno> alunos = serviceAluno.findByNomeContainingIgnoreCase("Italo");
        assertEquals("111111" , alunos.get(0).getMatricula()); 
    }

    @Test
    public void pesquisarAluno() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(86L);
        aluno.setNome("Paulo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("324123");
        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.pesquisarAluno("Paulo");
        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunoProcurado = (List<Aluno>) map.get("ListaDeAlunos");


        assertEquals("324123", alunoProcurado.get(0).getMatricula());
        
    }

    @Test
    public void listarAlunosInativos() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(55L);
        aluno.setNome("Miguel");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("453263");

        ModelAndView mv = alunoController.listaAlunosInativos();
        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunosInativosInicial = (List<Aluno>) map.get("alunosInativos");
        int qtdAlunosInativosInicial = alunosInativosInicial.size();

        serviceAluno.save(aluno);

        ModelAndView mv2 = alunoController.listaAlunosInativos();
        Map<String,Object> map2 =  mv2.getModel();
        List<Aluno> alunosInativosFinal = (List<Aluno>) map2.get("alunosInativos");
        int qtdAlunosInativosFinal = alunosInativosFinal.size();

        assertEquals(qtdAlunosInativosInicial + 1, qtdAlunosInativosFinal);

    }

    @Test
    public void listarAlunosAtivos() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(46L);
        aluno.setNome("Tebas");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("863513");

        ModelAndView mv = alunoController.listaAlunosAtivos();
        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunosAtivosInicial = (List<Aluno>) map.get("alunosAtivos");
        int qtdAlunosAtivosInicial = alunosAtivosInicial.size();

        serviceAluno.save(aluno);

        ModelAndView mv2 = alunoController.listaAlunosAtivos();
        Map<String,Object> map2 =  mv2.getModel();
        List<Aluno> alunosAtivosFinal = (List<Aluno>) map2.get("alunosAtivos");
        int qtdAlunosAtivosFinal = alunosAtivosFinal.size();

        assertEquals(qtdAlunosAtivosInicial + 1, qtdAlunosAtivosFinal);

    }

   
}
