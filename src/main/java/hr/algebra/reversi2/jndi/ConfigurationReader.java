package hr.algebra.reversi2.jndi;

import hr.algebra.reversi2.customExceptions.InvalidConfigKeyExc;
import hr.algebra.reversi2.enums.ConfigurationKey;

import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public class ConfigurationReader {
    private static ConfigurationReader _reader;
    private Hashtable<String, String> _enviroment;

    private ConfigurationReader(){
        _enviroment = new Hashtable<>();
        _enviroment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");

        String projectRoot = System.getProperty("user.dir");
        String relativePath = "files";
        String configPath = projectRoot + File.separator + relativePath;

        _enviroment.put(Context.PROVIDER_URL, "file:" + configPath);
    }

    public static ConfigurationReader getInstance(){
        if (_reader == null){
            _reader =  new ConfigurationReader();
        }
        return _reader;
    }

    public Integer readIntegerValueForKey(ConfigurationKey key) {
        String valueForKey = readStringValueForKey(key);
        if (valueForKey != null && !valueForKey.isEmpty()) {
            try {
                return Integer.parseInt(valueForKey);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public String readStringValueForKey(ConfigurationKey key){
        String valueForKey = "";

        try (InitialDirContextCloseable context = new InitialDirContextCloseable(_enviroment)){
            valueForKey = searchForKey(context,key);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return valueForKey;
    }

    private static String searchForKey(Context context, ConfigurationKey key)  {
        String fileName = "conf.properties";

        try {
            Object object = context.lookup(fileName);
            Properties props = new Properties();
            props.load(new FileReader(object.toString()));
            String value = props.getProperty(key.get_keyName());
            if (value == null){
                StringBuilder sbException = new StringBuilder();

                sbException.append("The key ");
                sbException.append(key.toString());
                sbException.append(" does not exits");

                throw new InvalidConfigKeyExc(sbException.toString());
            }
            return value;

        }
        catch(NamingException | IOException ex) {
            throw new RuntimeException("ERROR READING" + ex);
        }
    }
}
