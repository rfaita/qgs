package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum StatusOrdemServicoEnum {

    ABERTA("Aberta"), PROGRAMADA("Programada"), PENDENTE("Pendente"), ENCERRADA("Encerrada"), CANCELADA("Cancelada");

    private final String status;

    public String getStatus() {
        return status;
    }

    private StatusOrdemServicoEnum(String status) {
        this.status = status;
    }

}
