package com.qgs.ui.window;

import com.qgs.model.UsuarioProvedor;
import com.qgs.ui.QGSUI;
import com.qgs.util.security.SecurityRole;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioProvedorWindow extends Window {

    private final BeanFieldGroup<UsuarioProvedor> fieldGroup;

    private TabSheet tabSheet;
    private HorizontalLayout perfilTab;

    private Image perfilImage;
    private Upload perfilImageButton;

    private FormLayout perfilFormLayout;
    @PropertyId("usuario")
    private TextField txtUsuario;
    @PropertyId("nome")
    private TextField txtNome;
    @PropertyId("sobrenome")
    private TextField txtSobrenome;
    @PropertyId("sexo")
    private OptionGroup ogSexo;
    @PropertyId("role")
    private ComboBox cmbRole;
    @PropertyId("email")
    private TextField txtEmail;

    private HorizontalLayout usuariosTab;
    private Table table;
    private BeanContainer<Long, UsuarioProvedor> container;

    private HorizontalLayout footer;
    private Button btSave;

    private UsuarioProvedor usuarioProvedor;

    private QGSUI qgsui;

    private UsuarioProvedorWindow(QGSUI qgsui) {

        this.qgsui = qgsui;

        this.usuarioProvedor = this.qgsui.getSecurityUtils().getEmpresaPrincipal().getUsuarioProvedor();

        addStyleName("usuarioprovedorwindow");
        Responsive.makeResponsive(this);

        center();

        setCloseShortcut(KeyCode.ESCAPE);
        setModal(true);
        setResizable(false);
        setClosable(true);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));

        content.addComponent(getTabSheet());
        content.setExpandRatio(getTabSheet(), 1f);

        content.addComponent(getFooter());
        setContent(content);

        fieldGroup = new BeanFieldGroup<UsuarioProvedor>(UsuarioProvedor.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(this.usuarioProvedor);

        setWidth(500.0f, Unit.PIXELS);
        setHeight(90.0f, Unit.PERCENTAGE);

    }

    private TabSheet getTabSheet() {
        if (tabSheet == null) {
            tabSheet = new TabSheet();
            tabSheet.setSizeFull();
            tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
            tabSheet.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
            tabSheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);

            tabSheet.addComponent(getPerfilTab());
            if (this.qgsui.getSecurityUtils().hasPermission(SecurityRole.ADMIN)) {
                tabSheet.addComponent(getUsuariosTab());
            }
            tabSheet.addComponent(getPreferencesTab());

            tabSheet.addSelectedTabChangeListener((TabSheet.SelectedTabChangeEvent event) -> {
                if (event.getTabSheet().getSelectedTab().equals(getUsuariosTab())) {
                    getContainer().removeAllItems();
                    getContainer().addAll(this.qgsui.getUsuarioProvedorService().findUsuarioProvedorByProvedor());
                }
            });

        }
        return tabSheet;
    }

    private Component getPreferencesTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Preferences");
        root.setIcon(FontAwesome.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        Label message = new Label("Not implemented in this demo");
        message.setSizeUndefined();
        message.addStyleName(ValoTheme.LABEL_LIGHT);
        root.addComponent(message);
        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

        return root;
    }

    private Component getPerfilTab() {

        if (perfilTab == null) {
            perfilTab = new HorizontalLayout();
            perfilTab.setCaption("Perfil");
            perfilTab.setIcon(FontAwesome.USER);
            perfilTab.setWidth(100.0f, Unit.PERCENTAGE);
            perfilTab.setSpacing(true);
            perfilTab.setMargin(true);
            perfilTab.addStyleName("usuarioprovedorwindowperfil");

            VerticalLayout pic = new VerticalLayout();
            pic.setSizeUndefined();
            pic.setSpacing(true);

            pic.addComponent(getPerfilImage());
            pic.addComponent(getPerfilImageButton());

            perfilTab.addComponent(pic);

            perfilTab.addComponent(getPerfilFormLayout());
            perfilTab.setExpandRatio(getPerfilFormLayout(), 1);
        }
        return perfilTab;
    }

    private Component getUsuariosTab() {

        if (usuariosTab == null) {
            usuariosTab = new HorizontalLayout();
            usuariosTab.setCaption("Usuários");
            usuariosTab.setIcon(FontAwesome.USER);
            usuariosTab.setWidth(100.0f, Unit.PERCENTAGE);
            usuariosTab.setSpacing(true);
            usuariosTab.setMargin(true);
            usuariosTab.addStyleName("usuarioprovedorwindowusuarios");

            usuariosTab.addComponent(getTable());
            usuariosTab.setExpandRatio(getTable(), 1);
        }
        return usuariosTab;
    }

    private BeanContainer<Long, UsuarioProvedor> getContainer() {
        if (container == null) {
            container = new BeanContainer<Long, UsuarioProvedor>(UsuarioProvedor.class);
            container.setBeanIdResolver((UsuarioProvedor bean) -> bean.getId());
        }
        return container;
    }

    private Table getTable() {
        if (table == null) {
            table = new Table();
            table.setSizeFull();
            table.addStyleName(ValoTheme.TABLE_BORDERLESS);
            table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            table.addStyleName(ValoTheme.TABLE_COMPACT);
            table.setSelectable(true);

            table.setColumnCollapsingAllowed(true);
            table.setColumnReorderingAllowed(true);
            table.setContainerDataSource(getContainer());
            table.setSortAscending(false);

            table.addGeneratedColumn("role", (Table source, Object itemId, Object columnId) -> {
                Property<String> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(String.class)) {
                    return SecurityRole.valueOf(prop.getValue().toUpperCase()).getDescricao();
                }
                return "";
            });

            table.setVisibleColumns("id", "usuario", "nome", "sobrenome", "role");
            table.setColumnExpandRatio("premio", 1);
            table.setColumnHeaders("Código", "Usuário", "Nome", "Sobrenome", "Credencial");

            table.setImmediate(true);
        }

        return table;
    }

    private Image getPerfilImage() {
        if (perfilImage == null) {
            perfilImage = new Image(null, usuarioProvedor.getAvatar() == null ? new ClassResource("nobody-300px.jpg") : new ExternalResource(usuarioProvedor.getAvatar()));
            perfilImage.setWidth(100.0f, Unit.PIXELS);
        }
        return perfilImage;
    }

    private Upload getPerfilImageButton() {
        if (perfilImageButton == null) {

            ImageReceiver imageReceiver = new ImageReceiver();

            perfilImageButton = new Upload(null, imageReceiver);
            perfilImageButton.setButtonCaption("Alterar…");
            perfilImageButton.setImmediate(true);
            perfilImageButton.addSucceededListener(imageReceiver);
            perfilImageButton.addAttachListener(imageReceiver);
            JavaScript.getCurrent().execute("document.getElementsByClassName('gwt-FileUpload')[0].setAttribute('accept', 'image/*')");

        }
        return perfilImageButton;
    }

    private FormLayout getPerfilFormLayout() {
        if (perfilFormLayout == null) {
            perfilFormLayout = new FormLayout();
            perfilFormLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

            Label section = new Label("Dados de acesso");
            section.addStyleName(ValoTheme.LABEL_H4);
            section.addStyleName(ValoTheme.LABEL_COLORED);
            perfilFormLayout.addComponent(section);
            perfilFormLayout.addComponent(getTxtUsuario());
            perfilFormLayout.addComponent(getTxtEmail());

            section = new Label("Dados pessoais");
            section.addStyleName(ValoTheme.LABEL_H4);
            section.addStyleName(ValoTheme.LABEL_COLORED);
            perfilFormLayout.addComponent(section);

            perfilFormLayout.addComponent(getTxtNome());
            perfilFormLayout.addComponent(getTxtSobrenome());

            perfilFormLayout.addComponent(getOgSexo());

            perfilFormLayout.addComponent(getCmbRole());

        }
        return perfilFormLayout;
    }

    private TextField getTxtUsuario() {
        if (txtUsuario == null) {
            txtUsuario = new TextField("Usuário");
            txtUsuario.setEnabled(false);
        }
        return txtUsuario;
    }

    private TextField getTxtNome() {
        if (txtNome == null) {
            txtNome = new TextField("Nome");
        }
        return txtNome;
    }

    private TextField getTxtSobrenome() {
        if (txtSobrenome == null) {
            txtSobrenome = new TextField("Sobrenome");
        }
        return txtSobrenome;
    }

    private OptionGroup getOgSexo() {
        if (ogSexo == null) {
            ogSexo = new OptionGroup("Sexo");
            ogSexo.addItem("M");
            ogSexo.setItemCaption("M", "Masculino");
            ogSexo.addItem("F");
            ogSexo.setItemCaption("F", "Femínino");
            ogSexo.addStyleName("horizontal");
        }
        return ogSexo;
    }

    private TextField getTxtEmail() {
        if (txtEmail == null) {
            txtEmail = new TextField("Email");
            txtEmail.setWidth("100%");
            txtEmail.setRequired(true);
            txtEmail.setNullRepresentation("");
        }
        return txtEmail;
    }

    private ComboBox getCmbRole() {
        if (cmbRole == null) {
            cmbRole = new ComboBox("Credencial");
            cmbRole.addStyleName("horizontal");
            for (SecurityRole s : SecurityRole.values()) {
                cmbRole.addItem(s.getRole());
                cmbRole.setItemCaption(s.getRole(), s.getDescricao());
            }
        }
        return cmbRole;
    }

    private Component getFooter() {
        if (footer == null) {
            footer = new HorizontalLayout();
            footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
            footer.setWidth(100.0f, Unit.PERCENTAGE);

            footer.addComponent(getBtSave());
            footer.setComponentAlignment(getBtSave(), Alignment.TOP_RIGHT);
        }
        return footer;
    }

    private Button getBtSave() {
        if (btSave == null) {
            btSave = new Button("Alterar");
            btSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
            btSave.addClickListener((ClickEvent event) -> {
                try {
                    fieldGroup.commit();
                } catch (CommitException ex) {
                    Logger.getLogger(UsuarioProvedorWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return btSave;
    }

    public static void open(QGSUI qgsui) {
        Window w = new UsuarioProvedorWindow(qgsui);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

    class ImageReceiver implements Upload.Receiver, Upload.SucceededListener, Upload.AttachListener {

        private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        @Override
        public OutputStream receiveUpload(String filename, String mimeType) {
            return bos;
        }

        @Override
        public void uploadSucceeded(Upload.SucceededEvent event) {
            getPerfilImage().setSource(new ExternalResource("data:" + event.getMIMEType() + ";base64," + Base64.getMimeEncoder().encodeToString(bos.toByteArray())));
        }

        @Override
        public void attach(AttachEvent event) {
            JavaScript.getCurrent().execute("document.getElementsByClassName('gwt-FileUpload')[0].setAttribute('accept', 'image/*')");
        }
    };
}
