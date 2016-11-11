package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum CriterioSelecaoSetorEnum {

    BAIRRO("B"), CIDADE("C"), LOGRADOURO("L"), ESTADO("E"), PAIS("P");
    private String criterio;

    public String getCriterio() {
        return criterio;
    }

    private CriterioSelecaoSetorEnum(String criterio) {
        this.criterio = criterio;
    }
}
