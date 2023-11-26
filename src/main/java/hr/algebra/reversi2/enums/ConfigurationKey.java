package hr.algebra.reversi2.enums;

public enum ConfigurationKey {
    HOST("host"),
    SERVER_PORT("server.port"),
    CLIENT_PORT("client.port"),
    RMI_PORT("rmi.port");
    private String _keyName;

    private ConfigurationKey(String keyName){
        _keyName = keyName;
    }

    public String get_keyName() {
        return _keyName;
    }
}
