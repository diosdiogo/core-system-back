package com.core.system.core_system_back.enums;

public enum CompanyStatus {
    ATIVO("Ativo"),
    BLOQUEADO("Bloqueado"),
    TESTE("Teste"),
    INATIVO("Inativo"),
    PENDENTE("Pendente");

    private final String description;

    CompanyStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.name();
    }
}