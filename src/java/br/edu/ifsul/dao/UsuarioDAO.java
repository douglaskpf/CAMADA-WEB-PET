
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Usuario;
import java.io.Serializable;
import javax.persistence.Query;


public class UsuarioDAO<T> extends DAOGenerico<Usuario>implements Serializable {

    public UsuarioDAO(){
        super();
        super.setClassePersistente(Usuario.class);
        super.setOrdem("nome");// ordem padrão
    }
    
    
    public boolean login(String apelido, String senha){
        Query query = super.getEm().createQuery("from Usuario where upper(apelido) ="
        + ":apelido and upper(senha) = :senha and status = true");
        
        query.setParameter("apelido", apelido.toUpperCase());
        query.setParameter("senha", senha.toUpperCase());
        
        if(!query.getResultList().isEmpty()){
            return true;
        }else{
            return false;
        }       
          
     }
    
    
    public Usuario localizarPorNomeUsuario(String apelido){
        Query query = super.getEm().createQuery("from Usuario where upper(apelido) = :apelido");
        //:apelido é um parametro que a consulta espera
        
        
        query.setParameter("apelido", apelido.toUpperCase());
        Usuario obj =(Usuario) query.getSingleResult();
        return obj;
        
    }
}

