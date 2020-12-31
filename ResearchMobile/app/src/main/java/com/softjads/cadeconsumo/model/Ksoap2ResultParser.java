package com.softjads.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.softjads.cadeconsumo.util.ClasseADO;


/**

 * Ksoap2 for android - output parser
 * This class parses an input soap message
 * @author tamas.beres@helloandroid com, akos.birtha@helloandroid com
 *
 */

public class Ksoap2ResultParser {

    /**
     * Parses a single business object containing primitive types from the response
     * @param input soap message, one element at a time
     * @param theClass your class object, that contains the same member names and types for the response soap object
     * @return the values parsed
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */

    public static String MinhaparseBusinessObject(String input, Object output, ArrayList<Object> listaOpcao, String nTabela) throws NumberFormatException, IllegalArgumentException, IllegalAccessException, InstantiationException{

        Class classe = output.getClass();
        Class theClass = output.getClass();
        Field[] fields = theClass.getDeclaredFields();

        output.getClass().getTypeParameters();

        int contadoPassados =0;
        int contadorInicio = 0;
        int contadorFim = 0;
        int ContaArray = 0;


        output.getClass().getDeclaredMethods();

        String novotag = "";
        String Novoinput = input;

        String Tabela = nTabela+"=anyType" ;

        while (contadoPassados < input.length()) {

            ClasseADO nClasseADO = new ClasseADO();
            output = nClasseADO.IntanciarClasse(output.getClass().getSimpleName());

            if(Novoinput.contains(Tabela)){
                contadorInicio = Novoinput.indexOf(Tabela)+Tabela.length();
                contadorFim = Novoinput.indexOf(" };",Novoinput.indexOf(Tabela)) + 3;
                Novoinput = " " + Novoinput.substring(contadorInicio + 1 ,contadorFim);
                contadoPassados = contadoPassados +  contadorFim;


                for (int i = 0; i < fields.length; i++) {
                    Type type=fields[i].getType();
                    fields[i].setAccessible(true);

                    if (fields[i].getType().equals(String.class)) {
                        String tag = " " + fields[i].getName();   //"s" is for String in the above soap response example + field name for example Name = "sName"
                        if(Novoinput.contains(tag)){
                            String strValue = Novoinput.substring(Novoinput.indexOf(tag)+tag.length() + 1, Novoinput.indexOf(";", Novoinput.indexOf(tag)));
                            if (strValue.equals("anyType{}")){
                                strValue = "";
                            }
                            if(strValue.length()!=0){
                                fields[i].set(output, strValue);
                            }
                        }
                    }
                    else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
                        String tag = " " + fields[i].getName();   //"s" is for String in the above soap response example + field name for example Name = "sName"
                        if(Novoinput.contains(tag)){
                            String strValue = Novoinput.substring(Novoinput.indexOf(tag)+tag.length() + 1, Novoinput.indexOf(";", Novoinput.indexOf(tag)));
                            if (strValue.equals("anyType{}")){
                                strValue = "";
                            }
                            if(strValue.length()!=0){
                                fields[i].setInt(output, Integer.valueOf(strValue));
                            }
                        }
                    }
                    else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
                        String tag = " " + fields[i].getName();   //"s" is for String in the above soap response example + field name for example Name = "sName"
                        if(Novoinput.contains(tag)){
                            String strValue = Novoinput.substring(Novoinput.indexOf(tag)+tag.length() + 1, Novoinput.indexOf(";", Novoinput.indexOf(tag)));
                            if (strValue.equals("anyType{}")){
                                strValue = "";
                            }
                            if(strValue.length()!=0){
                                fields[i].setFloat(output, Float.valueOf(strValue));
                            }
                        }
                    }
                }
                listaOpcao.add(output);
            }
            else
            {
                break;
            }

            ContaArray = ContaArray + 1;

            Novoinput = input.substring(contadoPassados,input.length());
        }

        return Novoinput;
    }


    public static void parseBusinessObject(String input, Object output) throws NumberFormatException, IllegalArgumentException, IllegalAccessException, InstantiationException{
        Class theClass = output.getClass();
        Field[] fields = theClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Type type=fields[i].getType();
            fields[i].setAccessible(true);
            //detect String
            if (fields[i].getType().equals(String.class)) {
                String tag = fields[i].getName();   //"s" is for String in the above soap response example + field name for example Name = "sName"
                if(input.contains(tag)){
                    String strValue = input.substring(input.indexOf(tag)+tag.length(), input.indexOf(";", input.indexOf(tag)));

                    if(strValue.length()!=0){
                        fields[i].set(output, strValue);
                    }
                }
            }
            //detect int or Integer
            if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
                String tag = "i" + fields[i].getName() + "=";  //"i" is for Integer or int in the above soap response example+ field name for example Goals = "iGoals"
                if(input.contains(tag)){
                    String strValue = input.substring(input.indexOf(tag)+tag.length(), input.indexOf(";", input.indexOf(tag)));
                    if(strValue.length()!=0){
                        fields[i].setInt(output, Integer.valueOf(strValue));
                    }
                }
            }


            //detect float or Float

            if (type.equals(Float.TYPE) || type.equals(Float.class)) {
                String tag = "f" + fields[i].getName() + "=";
                if(input.contains(tag)){
                    String strValue = input.substring(input.indexOf(tag)+tag.length(), input.indexOf(";", input.indexOf(tag)));

                    if(strValue.length()!=0){
                        fields[i].setFloat(output, Float.valueOf(strValue));

                    }
                }
            }
        }
    }






}


