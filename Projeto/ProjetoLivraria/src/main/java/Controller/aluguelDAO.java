/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.aluguel;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class aluguelDAO extends DAO{
    clienteDAO controleCliente = new clienteDAO();
    livroDAO controleLivro = new livroDAO();
    aluguel Aluguel = new aluguel();
    private PreparedStatement pstdados = null;
    private ResultSet rsdados = null;
    private static final String inserirAluguel = "INSERT INTO aluguel (id_cliente, id_livro, data_aluguel, data_devolucao) VALUES (?, ?, ?, ?)";
    private static final String consultarAluguel = "SELECT * FROM aluguel ORDER BY id_aluguel";

    public boolean Inserir() {
        if(verificarCampos()){
            try {
                int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
                int concorrencia = ResultSet.CONCUR_UPDATABLE;
                pstdados = connection.prepareStatement(inserirAluguel, tipo, concorrencia);
                pstdados.setInt(1, Aluguel.getIdCliente());
                pstdados.setInt(2, Aluguel.getIdLivro());
                pstdados.setString(3, Aluguel.getDataAluguel());
                pstdados.setString(4, Aluguel.getDataDev());
                int resposta = pstdados.executeUpdate();
                pstdados.close();
                if (resposta == 1) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException erro) {
                System.out.println("Erro ao inserir: " + erro);
            }
        }
                return false;
    }
    
        public boolean ConsultarTodos() {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(consultarAluguel, tipo, concorrencia);
            rsdados = pstdados.executeQuery();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro ao executar consulta = " + erro);
        }
        return false;
    }
        
    public TableModel gerarTabela(){
        int linha = 0;
        DefaultTableModel modeloJT = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        try {
            
        int qntCol = rsdados.getMetaData().getColumnCount();
        for(int col = 1; col<= qntCol; col++){
            modeloJT.addColumn(rsdados.getMetaData().getColumnLabel(col));
        }

            while(rsdados != null && rsdados.next()){
                modeloJT.addRow(new Object[0]);
                modeloJT.setValueAt(rsdados.getInt("id_aluguel"), linha, 0);
                modeloJT.setValueAt(rsdados.getInt("id_cliente"), linha, 1);
                modeloJT.setValueAt(rsdados.getInt("id_livro"), linha, 2);
                modeloJT.setValueAt(rsdados.getString("data_aluguel"), linha, 3);
                modeloJT.setValueAt(rsdados.getString("data_devolucao"), linha, 4);

                linha++;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao gerar tabela: "+ erro);
        }
        return modeloJT;
    }
    
    public boolean verCliente(int id_cliente){
        controleCliente.conectar();
        boolean res = controleCliente.verificarIDCliente(id_cliente);
        controleCliente.desconectar();
        return res;
    }
    
        public boolean verLivro(int id_livro){
        controleLivro.conectar();
        boolean res = controleLivro.verificarIDLivro(id_livro);
        controleLivro.desconectar();
        return res;
    }
    
    public aluguel getAluguel(){
        return Aluguel;
    }
    
        public boolean verificarCampos(){

        if(Aluguel.getDataDev().trim().length() != 10){
            
            JOptionPane.showMessageDialog(null, "Campo da data de devolução está invalido!\nPor favor, verifique o campo de data de devolução", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(Aluguel.getDataAluguel().trim().length() != 10){
            JOptionPane.showMessageDialog(null, "Campo da data de empréstimo está invalido!\nPor favor, verifique o campo de data de empréstimo", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(Aluguel.getIdCliente() == 0 || numberExecao.verNum(Integer.toString(Aluguel.getIdCliente())) || verCliente(Aluguel.getIdCliente())){
            JOptionPane.showMessageDialog(null, "Campo ID do cliente está invalido!\nPor favor, verifique o campo do ID do cliente", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(Aluguel.getIdCliente() == 0 || numberExecao.verNum(Integer.toString(Aluguel.getIdLivro())) || verLivro((Aluguel.getIdLivro()))){
            JOptionPane.showMessageDialog(null, "Campo ID do livro está invalido!\nPor favor, verifique o campo do ID do livro", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
  
        return true;
    }

}
