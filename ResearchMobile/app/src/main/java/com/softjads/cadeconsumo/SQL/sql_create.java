package com.softjads.cadeconsumo.SQL;


public class sql_create {
	public static final int DBVERSION = 2;
	public static String DBNAME = "DBSOFTJADSPESQUISA.db";

	public static String TABLE_CONTROLE_INICIO = "CONTROLE_INICIO";
	public static String CREATE_CONTROLE_INICIO =
			"CREATE TABLE IF NOT EXISTS CONTROLE_INICIO (" +
					"  ID_ALUNO INTEGER," +
					"  ID_USUARIO INTEGER, " +
					"  NM_USUARIO   VARCHAR(15), " +
					"  DATA_INICIO VARCHAR(100), " +
					"  INICIO VARCHAR(100)," +
					"  LONGITUDE VARCHAR(100)," +
					"  LATITUDE VARCHAR(100)," +
					"  GRAVACAO VARCHAR(100)," +
					"  NM_PESQUISA VARCHAR (100),"+
					"  PREVISAO VARCHAR(100)"+
					");";

	public static String TABLE_PESQUISA_USUARIO = "PESQUISA_USUARIO";
	public static String CREATE_PESQUISA_USUARIO =
			" CREATE TABLE IF NOT EXISTS PESQUISA_USUARIO (" +
					"	    ID           INTEGER, " +
					"	    ID_PESQUISA  INTEGER,  " +
					"	    ID_USUARIO   INTEGER,  " +
					"	    ID_CLIENTE   INTEGER,  " +
					"	    DT_CADASTRO  VARCHAR(100)" +
					"); ";

	public static String TABLE_CONTROLE_FIM = "CONTROLE_FIM";
	public static String CREATE_CONTROLE_FIM =
			"CREATE TABLE IF NOT EXISTS CONTROLE_FIM (" +
					"  ID_ALUNO INTEGER," +
					"  ID_USUARIO INTEGER, " +
					"  NM_USUARIO   VARCHAR(15), " +
					"  DATA_FIM VARCHAR(100), " +
					"  FIM VARCHAR(100)," +
					"  LONGITUDE VARCHAR(100)," +
					"  LATITUDE VARCHAR(100)" +
					");";

	public static String TABLE_ALUNO = "ALUNO";
	public static String CREATE_ALUNO =
			"CREATE TABLE IF NOT EXISTS ALUNO (" +
					"  ID_ALUNO INTEGER PRIMARY KEY," +
					"  ID_ESCOLA INTEGER, " +
					"  ID_TURMA INTEGER, " +
					"  NOME VARCHAR(255)," +
					"  DT_NASC VARCHAR(20)," +
					"  SEXO VARCHAR(10)" +
					");";


	public static String TABLE_OC = "OC";
	public static String CREATE_OC =
			"CREATE TABLE IF NOT EXISTS OC (" +
					"  ID_OC INTEGER PRIMARY KEY," +
					"  MENSAGEM VARCHAR(255)," +
					"  ID_CLIENTE INTEGER, " +
					"  FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTE(ID_CLIENTE)" +
					");";

	public static String TABLE_OPCAO = "OPCAO";
	public static String CREATE_OPCAO =
			" CREATE TABLE IF NOT EXISTS OPCAO (" +
					" ID_OPCAO  VARCHAR(255), " +
					" MAXIMO INTEGER, " +
					" VALOR INTEGER, " +
					" OPCAO INTEGER, " +
					" PERSONALIZACAO VARCHAR (500), " +
					" MINIMO INTEGER, " +
					" ID_PERGUNTA VARCHAR(255), " +
					" SALTO VARCHAR (500), " +
					" ORDENA INTEGER); ";

	public static String TABLE_PERGUNTA = "PERGUNTAS";
	public static String CREATE_PERGUNTA =
			" CREATE TABLE IF NOT EXISTS PERGUNTAS (" +
					" ID_PERGUNTA VARCHAR(255), " +
					" DESCRICAO VARCHAR (500), " +
					" ID_BLOCO INTEGER, NUM_SUBFORMULARIO INTEGER, ID_PERGUNTA_JSON VARCHAR (500) , COD_PERGUNTA VARCHAR (500), ORDENA INTEGER); ";

	public static String TABLE_RESPOSTA = "RESPOSTA";
	public static String CREATE_RESPOSTA =
			" CREATE TABLE IF NOT EXISTS RESPOSTA (" +
					" ID_ALUNO INTEGER, " +
					" ID_PERGUNTA VARCHAR (250), " +
					" ID_OPCAO VARCHAR (250), " +
					" VALOR VARCHAR (250) , ID_OPCAO_PESSOA INTEGER, ID_ALIMENTO VARCHAR (250) ); ";

	public static String TABLE_BLOCO = "BLOCO";
	public static String CREATE_BLOCO =
			" CREATE TABLE IF NOT EXISTS BLOCO (" +
					" ID_BLOCO INTEGER, " +
					" DESCBLOCO VARCHAR(500), " +
					" INFORMACAO VARCHAR(100)); ";

	public static String TABLE_SALTO = "SALTO";
	public static String CREATE_SALTO =
			" CREATE TABLE IF NOT EXISTS SALTO (" +
					" ID_SALTO INTEGER, " +
					" SALTO_PARA VARCHAR(500), " +
					" PERGUNTA INTEGER, " +
					" ITEM_PERGUNTA INTEGER); ";

	public static String TABLE_CONFIGURACAO = "CONFIGURACAO";
	public static String CREATE_CONFIGURACAO =
			" CREATE TABLE IF NOT EXISTS CONFIGURACAO (" +
					" ID_CLIENTE INTEGER, " +
					" ID_PESQUISA INTEGER, " +
					" DSC_PESQUISA VARCHAR(100), " +
					" AUTOMATICO INTEGER, " +
					" PREVISAO VARCHAR(100) )" ;

	public static String TABLE_USUARIOS = "USUARIO";
	public static String CREATE_USUARIO =
			" CREATE TABLE IF NOT EXISTS USUARIO (" +
					" ID INTEGER, " +
					" NM_USUARIO VARCHAR(100), " +
					" SENHA VARCHAR(100), " +
					" ID_GRUPO INTEGER, " +
					" ID_CLIENTE INTEGER, " +
					" NOME VARCHAR(250)) ";

	public static String TABLE_ESCOLA = "ESCOLA";
	public static String CREATE_ESCOLA =
			" CREATE TABLE IF NOT EXISTS ESCOLA (" +
					" ID_ESCOLA INTEGER, " +
					" COD_UF INTEGER ,  " +
					" COD_MUNIC INTEGER,  " +
					" COD_BAIRRO INTEGER,  " +
					" NOME_ESCOLA VARCHAR(250) ," +
					" ENDERECO VARCHAR(200),  " +
					" NUMERO VARCHAR(10),  " +
					" TIPO VARCHAR(2),  " +
					" SUBTIPO VARCHAR(2), " +
					" REGIAO VARCHAR(2) )";

	public static String TABLE_TURMA = "TURMA";
	public static String CREATE_TURMA =
			" CREATE TABLE IF NOT EXISTS TURMA (" +
					" ID_TURMA INTEGER, " +
					" NOME_TURMA VARCHAR(20), "+
					" TURNO INTEGER, " +
					" ID_ESCOLA INTEGER) ";

	public static String TABLE_PESQUISA = "PESQUISA";
	public static String CREATE_PESQUISA =
			" CREATE TABLE IF NOT EXISTS PESQUISA (" +
					" ID_CLIENTE INTEGER," +
					" ID_PESQUISA INTEGER, " +
					" DSC_PESQUISA VARCHAR(20), "+
					" AUTOMATICO INTEGER," +
					" PREVISAO VARCHAR(100) ) ";

	public static String TABLE_ALIMENTO = "ALIMENTO";
	public static String CREATE_ALIMENTO =
			" CREATE TABLE IF NOT EXISTS ALIMENTO (" +
					" ID_ALUNO INTEGER, " +
					" ID VARCHAR (250), " +
					" CODIGO INTEGER, " +
					" DESCRICAO VARCHAR (250), " +
					" ALIMENTO_REFEICAO VARCHAR (250)," +
					" QUAL_E_ESSE_ITEM VARCHAR (250)," +
					" ALIMENTO_REFEICAO_ORDER INTEGER ) ";

	public static String TABLE_GRUPOS_ALIMENTOS = "GRUPOS_ALIMENTOS";
	public static String CREATE_GRUPOS_ALIMENTOS =
			" CREATE TABLE IF NOT EXISTS GRUPOS_ALIMENTOS (" +
					" ID_ALUNO INTEGER, " +
					" ID_ALIMENTO VARCHAR (250), " +
					" DESCRICAO VARCHAR (250)); ";

	public static String TABLE_MODO_PREPARACAO = "MODO_PREPARACAO";
	public static String CREATE_MODO_PREPARACAO =
			" CREATE TABLE IF NOT EXISTS MODO_PREPARACAO (" +
					" ID_ALUNO INTEGER, " +
					" ID_ALIMENTO VARCHAR (250), " +
					" DESCRICAO VARCHAR (250)); ";

	public static String TABLE_ADICOES = "ADICOES";
	public static String CREATE_ADICOES =
			" CREATE TABLE IF NOT EXISTS ADICOES (" +
					" ID_ALUNO INTEGER, " +
					" ID_ALIMENTO VARCHAR (250), " +
					" DESCRICAO VARCHAR (250)); ";

	public static String TABLE_MEDIDAS_CASEIRAS = "MEDIDAS_CASEIRAS";
	public static String CREATE_MEDIDAS_CASEIRAS =
			" CREATE TABLE IF NOT EXISTS MEDIDAS_CASEIRAS (" +
					" ID_ALUNO INTEGER, " +
					" ID_ALIMENTO VARCHAR (250), " +
					" DESCRICAO BLOB ) ";




}
