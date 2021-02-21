package br.com.app;

import br.com.app.api.model.auth.Credentials;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Map<String, Credentials> credentialsMap;

    static
    {
        credentialsMap = new HashMap<>(3);
        Credentials c1 = new Credentials("rafael", "123456", "read_only");
        Credentials c2 = new Credentials("daniel", "78910", "read_only");
        Credentials c3 = new Credentials("marcos", "24568", "read_only");
        credentialsMap.put(c1.getUsername(), c1);
        credentialsMap.put(c2.getUsername(), c2);
        credentialsMap.put(c3.getUsername(), c3);
    }

    public static Credentials get(String  username) {
        return credentialsMap.get(username);
    }
}
