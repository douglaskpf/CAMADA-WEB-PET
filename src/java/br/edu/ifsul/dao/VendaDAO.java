package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Venda;
import java.io.Serializable;



public class VendaDAO<T> extends DAOGenerico<Venda> implements Serializable {
 
    public VendaDAO() {
        super();
        super.setClassePersistente(Venda.class);
    }
    
    
}
