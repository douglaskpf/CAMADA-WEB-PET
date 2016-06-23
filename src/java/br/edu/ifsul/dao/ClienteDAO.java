
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Cliente;
import java.io.Serializable;


public class ClienteDAO<T> extends DAOGenerico<Cliente>implements Serializable {

    public ClienteDAO(){
        super();
        super.setClassePersistente(Cliente.class);
        super.setOrdem("nome");// ordem padrão
    }

}
