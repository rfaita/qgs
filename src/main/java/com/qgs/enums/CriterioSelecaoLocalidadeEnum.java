package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum CriterioSelecaoLocalidadeEnum {

    LOGRADOURO("Logradouro"), BAIRRO("Bairro"), CIDADE("Cidade"), ESTADO("Estado"), PAIS("País");
    private final String criterio;

    public String getCriterio() {
        return criterio;
    }

    private CriterioSelecaoLocalidadeEnum(String criterio) {
        this.criterio = criterio;
    }

}
