package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.dao.VendaDAO;
import br.edu.ifsul.modelo.Cliente;
import br.edu.ifsul.modelo.Produto;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.modelo.Venda;
import br.edu.ifsul.modelo.VendaItens;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "controleVenda")
@ViewScoped
public class ControleVenda implements Serializable {

    private VendaDAO<Venda> dao;
    private Venda objeto;
    private ProdutoDAO<Produto> daoProduto;
    private ClienteDAO<Cliente> daoCliente;
    private UsuarioDAO<Usuario> daoUsuario;
    private VendaItens item;
    private Boolean novoItem;

    public ControleVenda() {
        dao = new VendaDAO<>();
        daoProduto = new ProdutoDAO<>();
        daoCliente = new ClienteDAO<>();
        daoUsuario = new UsuarioDAO<>();
    }
    
    public void imprimeProdutos(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioProdutosPetShop", 
                parametros, daoProduto.getListaTodos());
    }
    
    public void imprimeVenda(Integer id){
        objeto = dao.localizar(id);
        List<Venda> listaVenda = new ArrayList<>();
        listaVenda.add(objeto);
        HashMap parametros = new HashMap();
        parametros.put("listaItens", objeto.getItens());
        UtilRelatorios.imprimeRelatorio("relatorioVendaPetShop", parametros,
                listaVenda);
    }

    
    public String listar() {
        return "/privado/venda/listar?faces-redirect=true";
    }

    public void novo() {
        objeto = new Venda();
        objeto.setData(Calendar.getInstance());
        // capturando o contexto do faces
        FacesContext context = FacesContext.getCurrentInstance();
        // extraindo a requisição do contexto
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        // extraindo a sessão da requisição
        HttpSession sessao = request.getSession();
        // extraindo o controleLogin da sessao
        ControleLogin controleLogin = (ControleLogin) sessao.getAttribute("controleLogin");
        // definindo o usuario logado na venda
        objeto.setUsuario(controleLogin.getUsuarioLogado());
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + e.getMessage());
        }
    }

    public void editar(Integer id) {
        try {
            objeto = dao.localizar(id);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar obejto: " + e.getMessage());
        }
    }

    public void remover(Integer id) {
        try {
            objeto = dao.localizar(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro a remover objeto: " + e.getMessage());
        }
    }

    public void novoItem() {
        item = new VendaItens();
        novoItem = true;
    }

    public void alterarItem(int index) {
        item = objeto.getItens().get(index);
        novoItem = false;
    }

    public void salvarItem() {
        if (novoItem) {
            objeto.adicionarItem(item);
        } else {
            atualizaValorTotal();
        }
        Util.mensagemInformacao("Operação realizada com sucesso");
    }

    public void atualizaValorUnitarioItem() {
        if (item != null) {
            if (item.getProduto() != null) {
                item.setValorUnitario(item.getProduto().getPreco());
            }
        }
    }

    private void atualizaValorTotal() {
        objeto.setValorTotal(0.00);
        Double total = 0.0;
        for (VendaItens vi : objeto.getItens()) {
            total += vi.getValorTotal();
        }
        objeto.setValorTotal(total);
    }

    public void calculaValorTotalItem() {
        if (item.getValorUnitario() != null && item.getQuantidade() != null) {
            item.setValorTotal(item.getValorUnitario() * item.getQuantidade());
        }
    }

    public void removerItem(int index) {
        objeto.removerItem(index);
        Util.mensagemInformacao("Item removido com sucesso");
    }

    public VendaDAO getDao() {
        return dao;
    }

    public void setDao(VendaDAO dao) {
        this.dao = dao;
    }

    public Venda getObjeto() {
        return objeto;
    }

    public void setObjeto(Venda objeto) {
        this.objeto = objeto;
    }

    public VendaItens getItem() {
        return item;
    }

    public void setItem(VendaItens item) {
        this.item = item;
    }

    public Boolean getNovoItem() {
        return novoItem;
    }

    public void setNovoItem(Boolean novoItem) {
        this.novoItem = novoItem;
    }

    public ProdutoDAO<Produto> getDaoProduto() {
        return daoProduto;
    }

    public void setDaoProduto(ProdutoDAO<Produto> daoProduto) {
        this.daoProduto = daoProduto;
    }

    public ClienteDAO<Cliente> getDaoCliente() {
        return daoCliente;
    }

    public void setDaoCliente(ClienteDAO<Cliente> daoCliente) {
        this.daoCliente = daoCliente;
    }

    public UsuarioDAO<Usuario> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(UsuarioDAO<Usuario> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

}