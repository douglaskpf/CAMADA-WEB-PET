package br.edu.ifsul.controle;

import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.AcessoUsuario;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author douglas
 */
@ManagedBean(name = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable {

    private UsuarioDAO<Usuario> dao;
    private Usuario usuarioLogado;
    private String apelido;
    private String senha;

    public ControleLogin() {

        dao = new UsuarioDAO<>();

    }

    public String paginaLogin() {
        return "/login?faces-redirect=true";

    }

    public String efetuarLogin() {

        if (dao.login(apelido, senha)) {

            usuarioLogado = dao.localizarPorNomeUsuario(apelido);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

            AcessoUsuario acesso = new AcessoUsuario(request.getRemoteAddr());
            usuarioLogado.adicionarAcesso(acesso);
            try {
                dao.merge(usuarioLogado);

            } catch (Exception e) {
                Util.mensagemErro("Erro ao persistir o acesso:" + e.getMessage());
            }
            Util.mensagemInformacao("Login efetuado com sucesso");
            return "/index";
        } else {
            Util.mensagemErro("Usuário ou senha inválidos");
            return "/login";

        }
    }

    public String efetuarLogout() {

        usuarioLogado = null;
        Util.mensagemInformacao("Logout efetuado com sucesso");
        return "/index";

    }

    public UsuarioDAO<Usuario> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<Usuario> dao) {
        this.dao = dao;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
