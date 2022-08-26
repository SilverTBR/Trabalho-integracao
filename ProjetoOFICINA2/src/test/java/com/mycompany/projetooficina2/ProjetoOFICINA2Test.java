
package com.mycompany.projetooficina2;

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
public class ProjetoOFICINA2Test {
    
    public ProjetoOFICINA2Test() {
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

    /**
     * Test of Emprestimo method, of class ProjetoOFICINA2.
     */
    @Test
    public void testEmprestimo() {
        ProjetoOFICINA2 projeto = new ProjetoOFICINA2();
        int Esperado = 2;
        int Obtido = projeto.Emprestimo(4,2);
        assertEquals(Esperado, Obtido);
    }
    
    /**
     * Test of Titulo method, of class ProjetoOFICINA2.
     */  
 
    @Test
    public void testTitulo() {
        ProjetoOFICINA2 projeto = new ProjetoOFICINA2();
        assertEquals("Sistema Livraria!", projeto.Titulo());   
    }
}
