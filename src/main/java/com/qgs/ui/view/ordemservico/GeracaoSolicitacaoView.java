package com.qgs.ui.view.ordemservico;

import com.qgs.model.Cliente;
import com.qgs.service.ClienteService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.ResizeView;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import javax.ejb.EJB;

@CDIView(value = GeracaoSolicitacaoView.VIEW_ID)
public final class GeracaoSolicitacaoView extends VerticalLayout implements View, ResizeView {

    public static final String VIEW_ID = "geracaosolicitacao";

    private TextField txtCPFCNPJCodNomeCliente;
    private TextField txtNome;
    private TextField txtEmail;
    private TextField txtCep;
    private TextField txtLogradouro;
    private TextField txtBairro;
    private TextField txtCidade;
    private TextField txtUF;
    private TextField txtPais;
    private Button btSearch;

    @EJB
    private ClienteService clienteService;

    public GeracaoSolicitacaoView() {

        setSizeFull();
        addStyleName("geracaosolicitacao");
        addStyleName("view");

        addComponent(getTxtCPFCNPJCodNomeCliente());
        addComponent(getBtSearch());
        addComponent(getTxtNome());
        addComponent(getTxtEmail());
        addComponent(getTxtLogradouro());
        addComponent(getTxtBairro());
        addComponent(getTxtCidade());
        addComponent(getTxtUF());
        addComponent(getTxtPais());

    }

    private TextField getTxtCPFCNPJCodNomeCliente() {
        if (txtCPFCNPJCodNomeCliente == null) {
            txtCPFCNPJCodNomeCliente = new TextField("Procura");
        }
        return txtCPFCNPJCodNomeCliente;
    }

    private TextField getTxtNome() {
        if (txtNome == null) {
            txtNome = new TextField("Nome");
            txtNome.setEnabled(Boolean.FALSE);
        }
        return txtNome;
    }

    private TextField getTxtEmail() {
        if (txtEmail == null) {
            txtEmail = new TextField("Email");
            txtEmail.setEnabled(Boolean.FALSE);
        }
        return txtEmail;
    }

    private TextField getTxtCep() {
        if (txtCep == null) {
            txtCep = new TextField("CEP");
            txtCep.setEnabled(Boolean.FALSE);
        }
        return txtCep;
    }

    private TextField getTxtLogradouro() {
        if (txtLogradouro == null) {
            txtLogradouro = new TextField("Logradouro");
            txtLogradouro.setEnabled(Boolean.FALSE);
        }
        return txtLogradouro;
    }

    private TextField getTxtBairro() {
        if (txtBairro == null) {
            txtBairro = new TextField("Bairro");
            txtBairro.setEnabled(Boolean.FALSE);
        }
        return txtBairro;
    }

    private TextField getTxtCidade() {
        if (txtCidade == null) {
            txtCidade = new TextField("Cidade");
            txtCidade.setEnabled(Boolean.FALSE);
        }
        return txtCidade;
    }

    private TextField getTxtUF() {
        if (txtUF == null) {
            txtUF = new TextField("UF");
            txtUF.setEnabled(Boolean.FALSE);
        }
        return txtUF;
    }

    private TextField getTxtPais() {
        if (txtPais == null) {
            txtPais = new TextField("País");
            txtPais.setEnabled(Boolean.FALSE);
        }
        return txtPais;
    }

    private Button getBtSearch() {
        if (btSearch == null) {
            btSearch = new Button();
            btSearch.setIcon(FontAwesome.SEARCH);
            btSearch.addClickListener((Button.ClickEvent event) -> {
                if (getTxtCPFCNPJCodNomeCliente().getValue() != null) {
                    doLoadCliente();
                }
            });
        }
        return btSearch;
    }

    private void doLoadCliente() {
        Cliente c = null;
        try {
            Long data = Long.parseLong(getTxtCPFCNPJCodNomeCliente().getValue());
            c = clienteService.findClienteByCPF(getTxtCPFCNPJCodNomeCliente().getValue());

            if (c == null) {
                c = clienteService.findById(data);
            }

            if (c == null) {
                c = clienteService.findClienteByCNPJ(getTxtCPFCNPJCodNomeCliente().getValue());
            }
        } catch (NumberFormatException e) {
            c = clienteService.findClienteByNome(getTxtCPFCNPJCodNomeCliente().getValue());
        }

        if (c == null) {
            QGSUI.showWarn("Cliente não localizado.");
            return;
        }

        setCliente(c);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void onResize(Page.BrowserWindowResizeEvent e) {
    }

    private void setCliente(Cliente c) {
        getTxtNome().setValue(c.getCliente());
        getTxtEmail().setValue(c.getEmail());
    }

}
