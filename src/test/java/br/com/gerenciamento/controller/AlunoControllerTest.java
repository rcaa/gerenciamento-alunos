@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarTodosOsAlunos() throws Exception {
        mockMvc.perform(get("/alunos"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void deveCriarUmNovoAluno() throws Exception {
        String novoAlunoJson = """
        {
            "nome": "Bianca C Neves",
            "idade": 23,
            "curso": "BCC"
        }
        """;

        mockMvc.perform(post("/alunos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(novoAlunoJson))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.nome").value("João Silva"));
    }
}
