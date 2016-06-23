
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.modelo.Cliente;
import br.edu.ifsul.modelo.Produto;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "controleCliente")
@SessionScoped
public class ControleCliente implements Serializable {
    private ClienteDAO<Cliente> dao;
    private Cliente objeto;
    private ProdutoDAO<Produto> daoProduto;
    private Produto produto;
    
    public ControleCliente(){
        dao = new ClienteDAO<>();
        daoProduto = new ProdutoDAO<>();
    }
    
    
      public void adicionarDesejo(){
        if (produto != null){
            if(!objeto.getDesejos_produtos().contains(produto)){
                objeto.getDesejos_produtos().add(produto);
                Util.mensagemInformacao("Desejo adicionado com sucesso");
            } else {
                Util.mensagemErro("Produto já existe na lista");
            } 
        }
    }
    
    public void removerDesejo(int index){
        produto = objeto.getDesejos_produtos().get(index);
        objeto.getDesejos_produtos().remove(produto);
        Util.mensagemInformacao("Desejo removido com sucesso!");
    }
    public String listar(){
        return "/privado/cliente/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Cliente();
        return "formulario";
    }
    
    public String salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }        
        if(persistiu){
            Util.mensagemInformacao(dao.getMensagem());
            return "listar";
        } else {
            Util.mensagemErro(dao.getMensagem());
            return "formulario";
        }                        
    }
    
    public String cancelar(){
        return "listar";
    }
    
    public String editar(Integer id){
        try {
            objeto = dao.localizar(id);
            return "formulario";
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));
            return "listar";
        }
    }
    
    public void remover(Integer id){
        try {
            objeto = dao.localizar(id);
            if(dao.remover(objeto)){
                Util.mensagemInformacao(dao.getMensagem());
            } else {
                Util.mensagemErro(dao.getMensagem());
            }            
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));            
        }
    }    
        

    public ClienteDAO getDao() {
        return dao;
    }

    public void setDao(ClienteDAO dao) {
        this.dao = dao;
    }

    public Cliente getObjeto() {
        return objeto;
    }

    public void setObjeto(Cliente objeto) {
        this.objeto = objeto;
    }
    
    
     public ProdutoDAO getDaoProduto() {
        return daoProduto;
    }

    public void setDaoProduto(ProdutoDAO daoProduto) {
        this.daoProduto = daoProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
