package com.softjads.cadeconsumo.util;

public class VariavelAPI {

    // Ambiente
    public final static String constante_variavel_papel = "{{EXIBIR_SOMENTE_PAPEL}}";
    public final static String constante_variavel_escolar = "{{EXIBIR_SOMENTE_ESCOLAR}}";
    public final static String constante_variavel_domiciliar = "{{EXIBIR_SOMENTE_DOMICILIAR}}";

    // idade
    public static final String constante_variavel_idade1 = "{{EXIBIR_SOMENTE_CRIANCA_MAIOR_OU_IGUAL_7ANOS}}";
    public static final String constante_variavel_idade2 = "{{EXIBIR_SOMENTE_CRIANCA_MENOR_7ANOS}}";
    public static final String constante_variavel_regra1 = "{{EXIBIR_SOMENTE_DOMICILIAR}}{{EXIBIR_SOMENTE_CRIANCA_MENOR_7ANOS}}";

    public static final String constante_variavel_regra2 = "{{EXIBIR_SOMENTE_DOMICILIAR}}{{EXIBIR_SOMENTE_CRIANCA_MAIOR_OU_IGUAL_7ANOS}}";

    // Personalização especifica
    public static final String getConstante_variavel_fechar = "{{FECHAR_APLICATIVO}}";
    public static final String constante_variavel_entrevistado = "{{NOME_CRIANCA}}";
    public static final String constante_variavel_refeicao_nome = "{{NOME_REFEICAO}}";
    public static final String constante_variavel_refeicao_nome_selecionada = "{{NOME_REFEICAO_SELECIONADA}}";
    public static final String contante_variavel_detalhar_alimento = "{{NOME_ALIMENTO_SELECIONADO_PARA_DETALHAR}}";
    public static final String contante_variavel_detalhar_alimento2 = "{{NOME_ALIMENTO_QUE_ESTA_SENDO_DETALHADO}}";
    public static final String constante_variavel_fotografar = "{{FOTOGRAFAR}}";
    public static final String constante_variavel_modo_preparo  = "{{MODOS_PREPARO_ALIMENTO}}";
    public static final String constante_variavel_adicoes  = "{{ADICOES_ALIMENTO}}";
    public static final String constante_variavel_medida  = "{{MEDIDAS_CASEIRAS_ALIMENTO}}";
    public static final String constante_variavel_alimento_excluir_editar = "{{LISTA_ALIMENTOS_CADASTRADOS_COM_OPCOES_EDITAR_E_EXCLUIR}}";
    public static final String constante_variavel_alimento_cadastrado = "{{LISTA_ALIMENTOS_CADASTRADOS_COM_OPCAO_MAIS}}";
    public static final String constante_variavel_alimento_cadastrado_simples = "{{LISTA_ALIMENTOS_CADASTRADOS_SIMPLES}}";
    public static final String constante_variavel_alimento_porcoes = "{{FOTOS_PORCOES_ALIMENTO}}";
    public static final String constante_variavel_alimento_utensilios = "{{FOTOS_UTENSILIOS_ALIMENTO}}";
    public static final String constante_variavel_refeicao_com_opcao_mais = "{{LISTAR_REFEICOES_CADASTRADAS_COM_OPCAO_MAIS}}";
    public static final String constante_variavel_refeicao = "{{LISTAR_REFEICOES_CADASTRADAS}}";

    // Extras
    public static final String constanteSelecione = "Selecione";

    // !!!!!!! Atenção verificar as descrições se mudar é necessaorio atualizar aqui
    public static final String constante_descricao_acrescentou = "Você/a criança acrescentou algo neste alimento?";
    public static final String  constante_descricao_domiciliar = "Casa";
    public static final String  constante_descricao_escolar = "Escola";
    public static final String constante_descricao_nascimento = "Idade";
    public static final String constante_descricoa_gravar = "Gravar";
    public static final String constante_descricao_alimento = "Nome do alimento";
    public static final String constante_descricao_medida = "Medida caseira";
    public static final String constante_descricao_modo_preparacao = "Modo de preparação";
    public static final String constante_descricao_quantidade = "Qual a quantidade da medida caseira utilizada? (ex.: 1 colher de sopa, 2 colheres de sopa, 3 colheres de sopa)";
    // INICIO/2
    public static final String constante_descricao_inicio_2_confirma = "Sim";
    public static final String constante_descricao_medida_caseira_detalhar =  "{{NOME_MEDIDA_CASEIRA_SELECIONADA_NA_TELA_ANTERIOR}}";

    public static final String constante_descricao_medida_caseira_1 = "Qual medida caseira você ou a criança utilizou para servir este alimento?";

    public static final String constant_chave_100 =  "INICIO/2";
    public static final String constant_chave_101 =  "FIM/1";
    public static final String constant_chave_102 =  "ALIMENTO/3";
    public static final String constant_chave_103 =  "ALIMENTO/2";
    public static final String constant_chave_104 =  "ALIMENTO/10";
    public static final String constant_chave_105 =  "INICIO/1";
    public static final String constant_chave_106 =  "DETALIM/2";
    public static final String constant_chave_107 =  "FIMDOMIC/2";
    public static final String constant_chave_108 =  "FIMDOMIC/1";
    public static final String constant_chave_109 =  "FIMDOMIC/3";
    // "FIMDOMIC/2" não tinha como saber pela id
    public static final String constant_chave_1010 =  "6EEEBF0B-75A5-4813-A9E1-C24CE1394E32";

    // caso iniciar
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC2 = "DETALIM/2/DOMIC";
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC1 = "DETALIM/1/DOMIC";

    public static final String contant_chave_inicair_anterior_DETALIMESC2 = "DETALIM/2/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC1 = "DETALIM/1/ESC";

    public static final String contant_chave_inicair_anterior_DETALIMPAPEL2 = "DETALIM/2/PAPEL";
    public static final String contant_chave_inicair_anterior_DETALIMPAPEL1 = "DETALIM/1/PAPEL";

    public static final String constant_chave_101_domic =  "FIMPAPEL/5";

    public static final String constant_foto_alimento = "{{EXIBIR_SOMENTE_TEM_FOTO_ALIMENTO";
    public static final String constant_foto_utensilio = "{{EXIBIR_SOMENTE_TEM_FOTO_UTENSILIO}}";
    public static final String constant_sem_foto = "{{EXIBIR_SOMENTE_SEM_FOTO}}";

}
