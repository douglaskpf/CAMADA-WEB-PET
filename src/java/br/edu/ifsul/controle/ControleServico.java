package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AnimalDAO;
import br.edu.ifsul.dao.ServicoDAO;
import br.edu.ifsul.modelo.Animal;
import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controleServico")
@ViewScoped
public class ControleServico implements Serializable {
    private ServicoDAO<Servico> dao;
    private Servico objeto;
    private AnimalDAO<Animal> daoAnimal;
        
    
    public ControleServico(){
        dao = new ServicoDAO<>();
        daoAnimal = new AnimalDAO<>();
       
    }
    
     public void gerarAgendamentos() {
        objeto.getAgendamentos().clear();
        objeto.gerarAgendamentos();
        Util.mensagemInformacao("Agendamentos gerados com sucesso!");
    }
    
    public String listar(){
        return "/privado/servico/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Servico();   
        objeto.setData(Calendar.getInstance());
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
        

    public ServicoDAO getDao() {
        return dao;
    }

    public void setDao(ServicoDAO dao) {
        this.dao = dao;
    }

    public Servico getObjeto() {
        return objeto;
    }

    public void setObjeto(Servico objeto) {
        this.objeto = objeto;
    }

    public AnimalDAO<Animal> getDaoAnimal() {
        return daoAnimal;
    }

    public void setDaoAnimal(AnimalDAO<Animal> daoAnimal) {
        this.daoAnimal = daoAnimal;
    }

  
  
}
