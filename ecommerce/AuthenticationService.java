import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthenticationService {

    private static String sessionId;
    private static Map<String, Object> sessionData = new HashMap<>();
    private static final String USER_DATA_FILE = "user_data.txt";

    @SuppressWarnings("static-access")
    public boolean authenticate(String username, String password, int loginType) {
        boolean isLogin = false;
        try {
            Scanner in = new Scanner(new FileReader(USER_DATA_FILE));
            while(in.hasNext()) {
                StringBuilder sb = new StringBuilder();
                sb.append(in.next());
                String outString = sb.toString();
                String[] arrUser = jsonDecodeUser(outString);

                String dataUsername = arrUser[0];
                String dataFullname = arrUser[1];
                String dataPassword = arrUser[2];
                int dataType = Integer.parseInt(arrUser[3]);

                if(username.equals(dataUsername) && password.equals(dataPassword) && loginType == dataType) {
                    isLogin = true;
                    this.sessionId = startSession();
                    setAttribute(this.sessionId, "username", username);
                    setAttribute(this.sessionId, "fullname", dataFullname);
                    setAttribute(this.sessionId, "type", loginType);
                    break;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (isLogin) {
            return true; // Autentikasi berhasil
        }
        else {
            return false; // Autentikasi gagal
        }
    }

    private String startSession() {
        String sessionId = UUID.randomUUID().toString();
        sessionData.put(sessionId, new HashMap<>());
        return sessionId;
    }

    @SuppressWarnings("unchecked")
    private void setAttribute(String sessionId, String attributeName, Object attributeValue) {
        if (sessionData.containsKey(sessionId)) {
            Map<String, Object> session = (Map<String, Object>) sessionData.get(sessionId);
            session.put(attributeName, attributeValue);
        } else {
            throw new IllegalArgumentException("Session not found");
        }
    }

    @SuppressWarnings({ "unused", "static-access", "unchecked" }) Object getAttribute(String attributeName) {
        if (sessionData.containsKey(this.sessionId)) {
            Map<String, Object> session = (Map<String, Object>) sessionData.get(this.sessionId);
            return session.get(attributeName);
        } else {
            throw new IllegalArgumentException("Session not found");
        }
    }

    private String[] jsonDecodeUser(String jsonString) {
        String trimmedJson = jsonString.substring(1, jsonString.length() - 1);

        String[] keyValuePairs = trimmedJson.split(",");

        String username = null;
        String fullname = null;
        String password = null;
        String type = null;

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");

            String key = keyValue[0].trim().replaceAll("\"", "");
            String value = keyValue[1].trim();

            if (key.equals("username")) {
                username = value.replaceAll("\"", "");
            }
            else if (key.equals("fullname")) {
                fullname = value.replaceAll("\"", "");
            }
            else if (key.equals("password")) {
                password = value.replaceAll("\"", "");
            }
            else if (key.equals("type")) {
                type = value.replaceAll("\"", "");
            }
        }

        String[] arrReturn = {username, fullname, password, type};
        return arrReturn;
    }
}