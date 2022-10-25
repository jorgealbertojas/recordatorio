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
    public static final String constante_variavel_entrevistado2 = "{{NOME_CRIANÇA}}";
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
    public static final String constante_descricao_quantidade = "Qual a quantidade da medida caseira utilizada? (ex.: 1 colher de sopa)";
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
    public static final String constant_chave_1013 = "PAPEL/2";
    public static final String constant_chave_1019 = "FIMDOMICDET/7";


    // "FIMDOMIC/2" não tinha como saber pela id
    public static final String constant_chave_1010 = "6EEEBF0B-75A5-4813-A9E1-C24CE1394E32";
    // "PAPEL/2" para saber o nome da refeição id_opcao
    public static final String constant_chave_1011 = "07820b7d-c13f-46df-9d5c-6ba388314f16";
    // "PAPEL/2" para saber o nome da refeição id_pergunta
    public static final String constant_chave_1012 = "D0BD426A-1D19-4ACA-B147-5AEB720D417A";
    // "INICIO/3" para saber o nome da crianca
    public static final String constant_chave_1014 = "9FE42CA6-AC40-4644-9D98-2CF9870D4B8B";
    // "NOTIFICACAO/1" gera notificacao
    public static final String constant_chave_1015 = "7895D828-80E8-4367-8A50-44504C950855";
    // "FIMDOMINIC/2 pega as resposta do DOMIC para detalhar as refeições
    public static final String constant_chave_1016 = "6EEEBF0B-75A5-4813-A9E1-C24CE1394E32";
    // "FIMESC/1 encerra as refeições com codigo ID
    public static final String constant_chave_1017 = "FEEE1EE3-DC81-4816-A266-064247EA1B3E";

    public static final String constant_chave_repetiu = "F2ED028D-80EF-476A-87DB-2E757389023F";

    public static final String constant_e_papel_sim = "0AC82ED0-B6AD-44C5-94BB-A58E2664C12F";
    public static final String constant_e_papel_nao = "96D4170A-7730-4B7B-A6C4-06A753EAE038";

    public static final String constant_casa = "15B42A5E-3A55-451A-B58A-F564257DC8C7";
    public static final String constant_escola = "C15EBC53-75D8-452C-BF2F-8A95B69D5C02";

    public static final String constant_preparado_aliemnto = "57fdf280-2e55-4097-9000-bff82c293c5f";
    public static final String constant_voce_acrescentou = "d28b2bf9-2fe7-4bf8-9d37-1bdef32d3fcb";

    public static final String constant_preparado_escola_aliemnto = "5c7db056-832b-4af7-b575-ea1ba452c7af";
    public static final String constant_voce_escola_acrescentou = "79aba2a8-2a3c-4946-85cb-15c612f65451";
    public static final String constant_voce_escola2_acrescentou = "d1885195-bc7c-4e98-85b5-0f80fabcfb0b";
    public static final String constant_voce_casa_acrescentou = "46b48b0c-c79a-47a8-a7dd-5f19070d446d";
    public static final String constant_voce_casa2_acrescentou = "9fd3bff3-d984-4a5f-ad2b-20bec1a9f8a5";


    public static final String constant_chave_escolar_youtube = "C15EBC53-75D8-452C-BF2F-8A95B69D5C02";
    public static final String constant_chave_domiciliar_youtube = "15B42A5E-3A55-451A-B58A-F564257DC8C7";

    public static final String constant_chave_no_more_fist_quest = "DE65F8E8-3EB1-4DA5-BCBD-417FC245BC1D";




    // caso iniciar
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC2 = "DETALIM/2/DOMIC";
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC1 = "DETALIM/1/DOMIC";

    public static final String contant_chave_inicair_anterior_DETALIMDOMIC8 = "DETALIM/8/DOMIC";
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC30 = "DETALIM/30/DOMIC";
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC40 = "DETALIM/40/DOMIC";
    public static final String contant_chave_inicair_anterior_DETALIMDOMIC50 = "DETALIM/50/DOMIC";



    public static final String contant_chave_inicair_anterior_DETALIMESC2 = "DETALIM/2/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC1 = "DETALIM/1/ESC";

    public static final String contant_chave_inicair_anterior_DETALIMESC31 = "DETALIM/31/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC41 = "DETALIM/41/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC51 = "DETALIM/51/ESC";

    public static final String contant_chave_inicair_anterior_DETALIMESC30 = "DETALIM/30/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC40 = "DETALIM/40/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC4 = "DETALIM/4/ESC";
    public static final String contant_chave_inicair_anterior_DETALIMESC5 = "DETALIM/5/ESC";

    public static final String contant_chave_inicair_anterior_DETALIMPAPEL2 = "DETALIM/2/PAPEL";
    public static final String contant_chave_inicair_anterior_DETALIMPAPEL1 = "DETALIM/1/PAPEL";



    public static final String constant_chave_101_domic =  "FIMPAPEL/5";

    public static final String constant_chave_133_domic =  "FIMPAPEL/6";

    public static final String constant_foto_alimento = "{{EXIBIR_SOMENTE_TEM_FOTO_ALIMENTO}}";
    public static final String constant_foto_utensilio = "{{EXIBIR_SOMENTE_TEM_FOTO_UTENSILIO}}";
    public static final String constant_sem_foto = "{{EXIBIR_SOMENTE_SEM_FOTO}}";

    public static final String constant_ativar_natificacao = "Ativar notificação para as próximas refeições";
    public static final String constant_ativar_natificacaoNao = "Não";

    public static final String constant_inserir_salto_video = "13F91073-7F58-4DA8-BB85-2D8B7BE3A059";

}
