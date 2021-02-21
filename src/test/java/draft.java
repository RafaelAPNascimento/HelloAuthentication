public class draft {

    public static void main(String[] args) {

        String token = "Bearer aeiou";

        token = token.substring(7, token.length());
        System.out.println(token);
        System.out.println(token.length());
    }
}
