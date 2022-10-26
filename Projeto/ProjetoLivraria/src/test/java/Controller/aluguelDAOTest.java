
package Controller;

import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Guilherme Andreotti
 */
public class aluguelDAOTest{
    
    aluguelDAO controle = new aluguelDAO();
    clienteDAO controleCliente = new clienteDAO();
    livroDAO controleLivro = new livroDAO();
    
    public aluguelDAOTest() {
    }
    
  
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    public int criaCliente(){
        
        int id = 0;
        controleCliente.setCaminhoTeste();
        controle.conectar();
        controle.excluir();
        controleCliente.conectar();
        controleCliente.excluir();     
        controleCliente.getCliente().setNome("Teste Aluguel");
        controleCliente.getCliente().setSobrenome("Teste");
        controleCliente.getCliente().setEstado("SP");
        controleCliente.getCliente().setEndereco("End. Teste");
        controleCliente.getCliente().setCidade("Testalopis");
        controleCliente.getCliente().setCPF("121.818.818-81");
        controleCliente.getCliente().setBairro("Bairro Teste"); 
        
        controleCliente.inserir();
        controleCliente.consultarTodos();
        
        ResultSet rs = controleCliente.getrsdados();
        try{
            if(rs.first()) {
               id = rs.getInt("id_cliente");
            }
          
        } catch (SQLException ex) {
        }  
        return id;
    }
    
    public int criaLivro(){
        
        int id = 0;
        controleLivro.setCaminhoTeste();
        controle.conectar();
        controle.excluir();
        controleLivro.conectar();
        controleLivro.excluirTodos();  
        
        controleLivro.getLivro().setTitulo("Teste Aluguel");
        controleLivro.getLivro().setGenero("Teste");
        controleLivro.getLivro().setEditora("Teste");
        controleLivro.getLivro().setNomeAutor("Teste");
        controleLivro.getLivro().setSobrenomeAutor("Teste");
        controleLivro.getLivro().setQntPgns(50);
        controleLivro.inserir();
        controleLivro.consultarTodos();
        ResultSet rs = controleLivro.getrsdados();
        try{
            if(rs.first()) {
               id = rs.getInt("id_livro");
            }
          
        } catch (SQLException ex) {
        }  
        return id;
    }
  
    public aluguelDAO criaAluguel(int idCli, int idLiv){
        
        controle.setCaminhoTeste();
        controle.conectar();
        controle.excluir();
        controle.getAluguel().setIdAluguel(1);
        controle.getAluguel().setDataAluguel("02-09-2002");
        controle.getAluguel().setDataDev("02-12-2022");
        controle.getAluguel().setIdCliente(idCli);
        controle.getAluguel().setIdLivro(idLiv);
        
        return controle;
        
    }
    
    @Test
    public void registroUnicoTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.Inserir();
        controle.ConsultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(true,rs.next());
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    } 
        
    @Test
    public void invalidoIDTest(){
        
        controle = criaAluguel(2, criaLivro());
        controle.Inserir();
        controle.ConsultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(false,rs.next());     
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    }
    
    @Test
    public void invalidoDataTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.getAluguel().setDataAluguel("02-09-202");
        controle.Inserir();
        controle.ConsultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(false,rs.next());     
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    }  
    
    @Test
    public void doisEmprestimosTest(){
        
        int idCli, idLiv = 0;
        idCli = criaCliente();
        idLiv = criaLivro();
        controle = criaAluguel(idCli, idLiv);
        controle.Inserir();
        
        controle.getAluguel().setIdLivro(idLiv);
        controle.Inserir();
        controle.ConsultarTodos();
                
        controle.consultarCount();          
        ResultSet rs = controle.getrsdados();
        
        try {
            assertEquals(1, rs.getInt(1));
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }
        controle.desconectar();
         
    }    
    
    @Test
    public void pesquisaTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.Inserir();
        controle.pesquisarAluguel("Teste Aluguel");
          
        try{
          ResultSet rs = controle.getrsdados();
          if(rs.first()) {
            assertEquals("Teste Aluguel",rs.getString("nome"));
          }
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }        
    }  
    
}