import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import br.com.gerenciamentoalunos.service.AlunoService; // Corrigido o pacote
import br.com.gerenciamentoalunos.repository.AlunoRepository; // Corrigido o pacote
import br.com.gerenciamentoalunos.model.Aluno; // Corrigido o pacote
import java.util.Optional; // Importando o Optional corretamente
import java.util.List; // Importando a List corretamente

public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;
    
    @InjectMocks
    private AlunoService alunoService;
    
    private Aluno aluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        aluno = new Aluno(1L, "João", "joao@email.com");
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

        assertNotNull(alunoBuscado);
        assertEquals(aluno.getId(), alunoBuscado.getId());
    }

    @Test
    public void testListarAlunos() {
        when(alunoRepository.findAll()).thenReturn(List.of(aluno));

        List<Aluno> alunos = alunoService.listarAlunos();

        assertFalse(alunos.isEmpty());
        assertEquals(1, alunos.size());
    }

    @Test
    public void testExcluirAluno() {
        doNothing().when(alunoRepository).deleteById(1L);

        alunoService.excluirAluno(1L);

        verify(alunoRepository, times(1)).deleteById(1L);
    }
}
