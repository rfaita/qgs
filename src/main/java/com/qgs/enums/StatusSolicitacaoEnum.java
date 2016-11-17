package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum StatusSolicitacaoEnum {

    ABERTA("Aberta"), PROGRAMADA("Programada"), PENDENTE("Pendente"), ENCERRADA("Encerrada"), CANCELADA("Cancelada");

    private final String status;

    public String getStatus() {
        return status;
    }

    private StatusSolicitacaoEnum(String status) {
        this.status = status;
    }

}
