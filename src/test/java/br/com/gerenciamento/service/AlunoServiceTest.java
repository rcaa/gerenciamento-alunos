import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import br.com.es.gerenciamentoalunos.service.AlunoService;
import br.com.es.gerenciamentoalunos.repository.AlunoRepository;
import br.com.es.gerenciamentoalunos.model.Aluno;

public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;
    
    @InjectMocks
    private AlunoService alunoService;
    
    private Aluno aluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        aluno = new Aluno(1L, "Bianca", "bianca@email.com");
    }

    @Test
    public void testAdicionarAluno() {
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        Aluno alunoAdicionado = alunoService.adicionarAluno(aluno);

        assertNotNull(alunoAdicionado);
        assertEquals(aluno.getNome(), alunoAdicionado.getNome());
    }

    @Test
    public void testBuscarAlunoPorId() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        Aluno alunoBuscado = alunoService.buscarAlunoPorId(1L);

     
