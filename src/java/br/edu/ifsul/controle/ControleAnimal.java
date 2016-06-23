package br.edu.ifsul.controle;




import br.edu.ifsul.dao.AnimalDAO;
import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.modelo.Animal;
import br.edu.ifsul.modelo.Cliente;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controleAnimal")
@ViewScoped
public class ControleAnimal implements Serializable {
    private AnimalDAO<Animal> dao;
    private Animal objeto;
    private ClienteDAO<Cliente> daoCliente;
    private Cliente cliente;
    
    public ControleAnimal(){
        dao = new AnimalDAO<>();
        daoCliente = new ClienteDAO<>();
    }
    
    public String listar(){
        return "/privado/animal/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Animal();        
    }
    
    public void salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }        
        if(persistiu){
            Util.mensagemInformacao(dao.getMensagem());            
        } else {
            Util.mensagemErro(dao.getMensagem());            
        }                        
    }    
    
    public void editar(Integer id){
        try {
            objeto = dao.localizar(id);            
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));            
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
        

    public AnimalDAO getDao() {
        return dao;
    }

    public void setDao(AnimalDAO dao) {
        this.dao = dao;
    }

    public Animal getObjeto() {
        return objeto;
    }

    public void setObjeto(Animal objeto) {
        this.objeto = objeto;
    }

    public ClienteDAO<Cliente> getDaoCliente() {
        return daoCliente;
    }

    public void setDaoCliente(ClienteDAO<Cliente> daoCliente) {
        this.daoCliente = daoCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
