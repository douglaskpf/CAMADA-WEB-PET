
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Animal;
import java.io.Serializable;

/**
 *
 * @author Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 */
public class AnimalDAO<T> extends DAOGenerico<Animal>implements Serializable {

    public AnimalDAO(){
        super();
        super.setClassePersistente(Animal.class);
        super.setOrdem("nome");// ordem padrão
    }

}
