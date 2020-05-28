package com.d3x.gym.view;

/**
 * Esta classe define as diferentes visualizacoes disponiveis para serializacoes
 */
public interface View {
    /**
     * Visualizacao principal com os principais atributos
     */
    public static interface Main {
    }
    /**
     * Visualizacao com todos os atributos
     * Inclui tudos os atributos marcados com Main
     */
    public static interface All extends Main {
    }
    /**
     * Visualizacao alternativa
     */
    public static interface Alternative {
    }
}