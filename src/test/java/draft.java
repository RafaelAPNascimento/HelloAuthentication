import br.com.app.api.model.auth.JWT;

public class draft {

    public static void main(String[] args) {

        String token = "Bearer eyJhbGciOiJIUzI1NiIsICJ0eXAiOiJqd3QifQ.eyJzY29wZSI6InJlYWRfb25seSIsImlzcyI6InJhZmFlbC5zZW5pb3IuZW5naW5lZXIiLCJuYW1lIjoiZGFuaWVsIiwiZXhwIjoxNjE0NDI3ODczLCJpYXQiOjE2MTQ0Mjc3NTMsImp0aSI6IjY0ZDk3ZGE2LWEwMzAtNDdmMi05NzQ1LWFhMGEwZjExNDA0MCJ9.kTgimKTz86JILqrPBvCvsDaNzkIV2-d5zdHdCyEYEAQ";

        String accessToken = token.substring(7);

        System.out.println(accessToken);
        char[] chars = accessToken.toCharArray();
        chars[60] = 'k';
        accessToken = String.valueOf(chars);


    }
}
