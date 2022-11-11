
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
public class AluguelDAOTest{
    
    AluguelDAO controle = new AluguelDAO();
    ClienteDAO controleCliente = new ClienteDAO();
    LivroDAO controleLivro = new LivroDAO();
    String nome = "Teste Aluguel";
    
    public AluguelDAOTest() {
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
        controleLivro.excluir();  
        
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
  
    public AluguelDAO criaAluguel(int idCli, int idLiv){
        
        controle.setCaminhoTeste();
        controle.conectar();
        controle.excluir();
        controle.getAluguel().setIdAluguel(1);
        controle.getAluguel().setDataAluguel("02-09-2021");
        controle.getAluguel().setDataDev("02-12-2022");
        controle.getAluguel().setIdCliente(idCli);
        controle.getAluguel().setIdLivro(idLiv);
        
        return controle;
        
    }
    
     /**
     * Testando o registro normal de um aluguel, com cliente e livro
     */
    
    @Test
    public void registroSimplesTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.inserir();
        controle.consultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(true,rs.next());
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    } 
    
    /**
     * Teste com data de devolução inválida.
     */
    
    @Test
    public void invalidoDataDevTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.getAluguel().setDataDev("02-09-202");
        controle.inserir();
        controle.consultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(false,rs.next());     
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    }  
    
     /**
     * Teste inserindo outra data na data de devolução.
     */  
    
    @Test
    public void alterandoDataDevTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.getAluguel().setDataDev("02-09-2022");
        controle.inserir();
        controle.consultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(true,rs.next());     
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    }
    
     /**
     *  Teste alterando a data de aluguel, que já vem definida.
     */
    
    @Test
    public void alterandoDataAluguelTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.getAluguel().setDataDev("02-12-2021");
        controle.inserir();
        controle.consultarTodos();
        ResultSet rs = controle.getrsdados();
        try{
          assertEquals(true,rs.next());     
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }  
    }  
    
     /**
     * Pesquisando na tabela de aluguel.
     */
    
    @Test
    public void pesquisaTabelaAluguelTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.inserir();
       
        controle.pesquisarAluguel(nome);        
        try{
          ResultSet rs = controle.getrsdados();
          if(rs.first()) {
            assertEquals(nome,rs.getString("nome"));
          }
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }        
    }  
      /**
     * Pesquisando na tabela de clientes.
     */   
    @Test
    public void pesquisaTabelaClienteTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.inserir();
        controle.consultarTodos();
        
        controle.getClienteModel(nome);        
        try{
          ResultSet rs = controle.getrsdados();
          if(rs.first()) {
            assertEquals(nome,rs.getString("nome"));
          }
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }        
    }  
      /**
     * Pesquisando na tabela de livros.
     */   
    @Test
    public void pesquisaTabelaLivroTest(){
        
        controle = criaAluguel(criaCliente(), criaLivro());
        controle.inserir();
        controle.consultarTodos();
        
        controle.getLivroModel(nome);        
        try{
          ResultSet rs = controle.getrsdados();
          if(rs.first()) {
            assertEquals(nome,rs.getString("nome"));
          }
          controle.desconectar();
          
        } catch (SQLException ex) {
            fail("Erro ao executar o teste, gerou uma falha de conexão!");
        }        
    }  
    /**
     * Registrando algo com ID inexistente. 
     */
    @Test
    public void idInexistenteTest(){
        
        controle = criaAluguel(2, criaLivro());
        controle.inserir();
  
        ResultSet rs = controle.getrsdados();
       
        assertEquals(null,rs);     
        controle.desconectar();        
    }
   
}
