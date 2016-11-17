package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum TipoBaixaSolicitacaoEnum {

    ENCERRADA("Encerrada"), CANCELADA("Cancelada");

    private final String tipoBaixa;

    public String getTipoBaixa() {
        return tipoBaixa;
    }

    private TipoBaixaSolicitacaoEnum(String tipoBaixa) {
        this.tipoBaixa = tipoBaixa;
    }

}
