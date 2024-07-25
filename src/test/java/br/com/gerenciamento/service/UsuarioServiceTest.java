package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.UsuarioNotSavedException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
    @SpringBootTest
    public class UsuarioServiceTest {

        @Autowired
        private ServiceUsuario serviceUsuario;
        @Test
        public void salvarUsuario() {
            Usuario usuario = new Usuario();
            usuario.setId(1L);
            usuario.setUser("Stargazer");
            usuario.setEmail("teste@teste.com");
            usuario.setSenha("monstergelado");

            try {
                serviceUsuario.salvarUsuario(usuario);
            } catch (Exception e) {
                fail("Exception thrown while saving user: " + e.getMessage());
            }

            assertNotNull(usuario.getId());
        }

    @Test
    public void salvarUsuarioSemId(){
        Usuario usuario = new Usuario();
        usuario.setUser("Stargazer");
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("monstergelado");
        try {
            serviceUsuario.salvarUsuario(usuario);
            if(usuario.getId() == null){
                throw new UsuarioNotSavedException("O usuario foi salvo sem ID");
            }
        } catch (Exception e) {
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        assertNotNull(usuario.getId());
    }

    @Test
    public void atualizarEmailUsuario() {

        Usuario usuario = new Usuario();
        usuario.setUser("Stargazer");
        usuario.setEmail("EMAIL@IRADO.com");
        usuario.setSenha("mangoloucoGelado.com.br.pe.ponto");

        try {
            serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        assertNotNull(usuario.getId());

        Usuario usuarioAtualizado = null;
        try {
            usuarioAtualizado = serviceUsuario.getById(usuario.getId());
            usuarioAtualizado.setEmail("novoemail@teste.com");
            serviceUsuario.salvarUsuario(usuarioAtualizado);
        } catch (Exception e) {
            fail("Exception thrown while updating user email: " + e.getMessage());
        }

        try {
            Usuario usuarioVerificado = serviceUsuario.getById(usuario.getId());
            assertEquals(usuarioAtualizado.getEmail(), usuarioVerificado.getEmail());
        } catch (Exception e) {
            fail("Exception thrown while verifying user email: " + e.getMessage());
        }
    }

    @Test
    public void excluirUsuario(){

        Usuario usuario = new Usuario();
        usuario.setUser("Stargazer");
        usuario.setEmail("EMAIL@IRADO.com");
        usuario.setSenha("mangoloucoGelado.com.br.pe.ponto");

        try {
            serviceUsuario.salvarUsuario(usuario);
        } catch (Exception e) {
            fail("Exception thrown while saving user: " + e.getMessage());
        }

            serviceUsuario.deleteById(1L);
    }
}

