package com.qgs.util.security;

/**
 *
 * @author rafael
 */
public enum SecurityRole {

    ADMIN("admin", "Administrador", 10000),
    SUB_ADMIN("sub_admin", "Sub-Administrador", 1000),
    USER("user", "Usuário", 100),
    QUEST("quest", "Anônimo", 10);

    private final String role;
    private final String descricao;
    private final Integer priority;

    private SecurityRole(String role, String descricao, Integer priority) {
        this.role = role;
        this.priority = priority;
        this.descricao = descricao;
    }

    public String getRole() {
        return role;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getDescricao() {
        return descricao;
    }

}
