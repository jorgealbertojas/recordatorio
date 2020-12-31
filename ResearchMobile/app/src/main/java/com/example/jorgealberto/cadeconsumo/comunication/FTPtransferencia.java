package com.example.jorgealberto.cadeconsumo.comunication;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jorgealberto.cadeconsumo.library.MyConstant;


public class FTPtransferencia  extends AsyncTask<String, Void, Boolean>{

    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        try{
            PreencherBKP(MyConstant.storage,"GRAVACAO");
        }
        finally{
            return null;
        }

    }

    private void envioFTP(String login, String senha, String diretorio, String nomeArquivo) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(MyConstant.ip_servidor, 21);
            ftp.login(login, senha);
            FileInputStream arqEnviar = new FileInputStream(diretorio+nomeArquivo);
            ftp.setFileTransferMode(ftp.BINARY_FILE_TYPE);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.storeFile(nomeArquivo, arqEnviar);
            ftp.logout();
            ftp.disconnect();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean ftpDownload(String login, String senha,String srcFilePath, String desFilePath)
    {
        FTPClient ftp = new FTPClient();
        boolean status = false;
        try
        {
            ftp.connect(MyConstant.ip_servidor, 21);
            ftp.login(login, senha);
            try {
                FileOutputStream desFileStream = new FileOutputStream(desFilePath);;
                //Tipo de arquivo
                ftp.setFileType(FTP.BINARY_FILE_TYPE);

                ftp.enterLocalPassiveMode();

                //Faz o download do arquivo
                status = ftp.retrieveFile(srcFilePath, desFileStream);


                //Fecho o output
                desFileStream.close();
                ftp.logout();
                ftp.disconnect();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + MyConstant.storage +"/pesquisaandroid.apk"), "application/vnd.android.package-archive");
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);

                return status;


            } catch (Exception e) {
                Log.e("Log", "download falhou");

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;

    }

    private void PreencherBKP(String Origem,String inicioArquivo){

        try{


            File TODOS_File = new File(Origem);


            File list[] = TODOS_File.listFiles();


            int numeroTotal = 0;

            for (int i =0; i < list.length; i++)
            {
                int taminico = inicioArquivo.length();


                int tamArquivo = list[i].getName().length();

                if (tamArquivo > taminico) {


                    if (inicioArquivo.toString().equals(list[i].getName().substring(0, taminico))) {

                        numeroTotal = numeroTotal + 1;



                        envioFTP("ProjetoERICA", "49P3ric439", Origem, list[i].getName());

                        File file = new File(Origem+"bkp_gravacao_voz/");
                        if (!file.exists()) {
                            file.mkdirs();
                        }


                        bkp(Origem+list[i].getName(),Origem+"bkp_gravacao_voz/"+list[i].getName());
                        DeleteBKP(Origem+list[i].getName());


                    }

                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    private void bkp(String Origem, String Destino) throws IOException{
        final String inFileName = Origem;

        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);



        //String outFileName = Environment.getExternalStoragePublicDirectory()+"/database_copy.db";
        String outFileName = Destino;

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();

    }

    private boolean DeleteBKP(String Origem){
        final String inFileName = Origem;
        File dbFile = new File(inFileName);
        return  dbFile.delete();
    }




}

