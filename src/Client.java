import java.net.*;
import java.io.*;

import org.apache.commons.cli.*;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

class Client {

  public static void main( String args[] ){

    CommandLine cmd = _parse( args );

    String sUrl = cmd.getOptionValue("url");

    String keystore = cmd.getOptionValue("keystore");
    String password = cmd.getOptionValue("password");
    if(password == null) password = new String(System.console().readPassword("Enter JKS passphrase: "));
      
    System.setProperty("javax.net.ssl.trustStoreType", "jks");
    System.setProperty("javax.net.ssl.keyStore", keystore );
    System.setProperty("javax.net.ssl.trustStore", keystore );
    System.setProperty("javax.net.ssl.keyStorePassword", password );
    System.setProperty("javax.net.ssl.keyStorePassword", password );
    System.setProperty("javax.net.debug", cmd.hasOption("verbose") ? "ssl" : "" );

    _connect(sUrl);
  }

  private static CommandLine _parse( String args[] )
  {
    Options options = new Options();

    Option oUrl = new Option("u", "url", true, "URL to make client cert connection to.");
    Option oKeystore = new Option("k", "keystore", true, "Location of Keystore (JKS) file.");
    Option oPassword = new Option("p", "password", true, "Password for Keystore (JKS) file.");
    Option oVerbose = new Option("v", "verbose", false, "Enable verbose output");

    oUrl.setRequired(true);
    oKeystore.setRequired(true);
    oPassword.setRequired(false);

    options.addOption(oUrl);
    options.addOption(oKeystore);
    options.addOption(oPassword);
    options.addOption(oVerbose);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd;

    try{
      cmd = parser.parse (options, args);
      return cmd;
    } catch(ParseException e) {
      System.out.println( e.getMessage() );
      formatter.printHelp( "jks-tester", options );
      System.exit(1);
      return null;
    }
  }

  private static void _connect( String sUrl )
  {
    //System.setProperty("javax.net.debug", ""); //Set ebug to SSL for full output

    SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

    try{
      
      URL url = new URL( sUrl );
      HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

      //conn.setRequestProperty("Accept","*/*");
      conn.setSSLSocketFactory(sslsocketfactory);

      conn.connect();
      int responsecode = conn.getResponseCode();
      System.out.println( "HttpsURLConnection::responseCode " + responsecode );

      //TODO: Move to try catch
      InputStream inputstream = conn.getInputStream();
      if(responsecode != HttpsURLConnection.HTTP_OK ) inputstream = conn.getErrorStream();
      System.out.println("inputstream :: " + inputstream );

      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      String string = null;
      while ((string = bufferedreader.readLine()) != null) {
          System.out.println("Received " + string);
      }
    } catch(MalformedURLException e){
      System.out.println( "Bad URL :: " + e.getMessage() );
    } catch(IOException e){
      System.out.println( "Certificate IOException :: " + e.getMessage() + "\n" + e );
    }
    return;
  }

}
