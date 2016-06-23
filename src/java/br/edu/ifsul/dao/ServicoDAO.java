
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Servico;
import java.io.Serializable;


public class ServicoDAO<T> extends DAOGenerico<Servico>implements Serializable {

    public ServicoDAO(){
        super();
        super.setClassePersistente(Servico.class);
        super.setOrdem("nome");// ordem padrão
    }

}
