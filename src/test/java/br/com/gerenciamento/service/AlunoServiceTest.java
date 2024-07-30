    package br.com.gerenciamento.service;

    import br.com.gerenciamento.enums.Curso;
    import br.com.gerenciamento.enums.Status;
    import br.com.gerenciamento.enums.Turno;
    import br.com.gerenciamento.model.Aluno;
    import jakarta.persistence.EntityNotFoundException;
    import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.junit.*;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.junit4.SpringRunner;

    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class AlunoServiceTest {

        @Autowired
        private ServiceAluno serviceAluno;

        @Test
        public void getById() {
            Aluno aluno = new Aluno();
            aluno.setId(1L);
            aluno.setNome("Vinicius");
            aluno.setTurno(Turno.NOTURNO);
            aluno.setCurso(Curso.ADMINISTRACAO);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("123456");
            this.serviceAluno.save(aluno);

            Aluno alunoRetorno = this.serviceAluno.getById(1L);
            Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
        }

        @Test
        public void salvarSemNome() {
            Aluno aluno = new Aluno();
            aluno.setId(1L);
            aluno.setTurno(Turno.NOTURNO);
            aluno.setCurso(Curso.ADMINISTRACAO);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("123456");
            Assert.assertThrows(ConstraintViolationException.class, () -> {
                    this.serviceAluno.save(aluno);});
        }
        
        @Test
        public void GetMatricula() {
            Aluno aluno = new Aluno();
            aluno.setId(2L);
            aluno.setNome("Ana");
            aluno.setTurno(Turno.MATUTINO);
            aluno.setCurso(Curso.BIOMEDICINA);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("654321");
            this.serviceAluno.save(aluno);

            Aluno alunoRetorno = this.serviceAluno.getByMatricula("654321");
            Assert.assertTrue(alunoRetorno.getNome().equals("Ana"));
        }
        
        @Test
        public void UpdateAluno() {
            Aluno aluno = new Aluno();
            aluno.setId(3L);
            aluno.setNome("Carlos");
            aluno.setTurno(Turno.MATUTINO);
            aluno.setCurso(Curso.DIREITO);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("111222");
            this.serviceAluno.save(aluno);

            aluno.setNome("Carlos Eduardo");
            this.serviceAluno.save(aluno);

            Aluno alunoRetorno = this.serviceAluno.getById(3L);
            Assert.assertTrue(alunoRetorno.getNome().equals("Carlos Eduardo"));
            }
        
        @Test
        public void removeAluno() {
            Aluno aluno = new Aluno();
            aluno.setId(4L);
            aluno.setNome("Mariana");
            aluno.setTurno(Turno.NOTURNO);
            aluno.setCurso(Curso.BIOMEDICINA);
            aluno.setStatus(Status.ATIVO);
            aluno.setMatricula("789012");
            this.serviceAluno.save(aluno);

            this.serviceAluno.deleteById(aluno.getId());

            Assert.assertThrows(EntityNotFoundException.class, () -> {
                this.serviceAluno.getById(4L);
            });
            }
        
        @Test
        public void getAllAlunos() {
            Aluno aluno1 = new Aluno();
            aluno1.setId(5L);
            aluno1.setNome("João");
            aluno1.setTurno(Turno.MATUTINO);
            aluno1.setCurso(Curso.INFORMATICA);
            aluno1.setStatus(Status.ATIVO);
            aluno1.setMatricula("333444");
            this.serviceAluno.save(aluno1);
        
            Aluno aluno2 = new Aluno();
            aluno2.setId(6L);
            aluno2.setNome("Fernanda");
            aluno2.setTurno(Turno.MATUTINO);
            aluno2.setCurso(Curso.BIOMEDICINA);
            aluno2.setStatus(Status.ATIVO);
            aluno2.setMatricula("555666");
            this.serviceAluno.save(aluno2);
        
            List<Aluno> alunos = this.serviceAluno.findAll();
            Assert.assertTrue(alunos.size() >= 2);
            Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("João")));
            Assert.assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Fernanda")));
        }
    }