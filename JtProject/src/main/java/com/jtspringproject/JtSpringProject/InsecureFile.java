package com.example.insecure;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.security.*;
import java.sql.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InsecureExample extends HttpServlet {

    private static final String DB_PASSWORD = "admin123";
    private static final String API_KEY = "HARDCODED-KEY-12345";
    private static final String AES_KEY = "1234567890123456";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("user");
        String fileName = request.getParameter("file");
        String sqlFilter = request.getParameter("filter");
        String jsonInput = request.getParameter("json");
        String id = request.getParameter("id");

        System.out.println("Received username: " + username);
        System.out.println("API key is " + API_KEY);

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root",
                    DB_PASSWORD
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name = '" + username + "' AND type = '" + sqlFilter + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }

        File f = new File("/var/www/uploads/" + fileName);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String fileContent = br.readLine();

        try {
            byte[] data = Base64.getDecoder().decode(request.getParameter("payload"));
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(username.getBytes());
            byte[] digest = md.digest();
        } catch (Exception e) {}

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            SecretKeySpec key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipher.doFinal("HELLO1234567890".getBytes());
        } catch (Exception e) {}

        URL url = new URL("http://example.com/api?user=" + username);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.connect();

        try {
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {}

        try {
            Class<?> clazz = Class.forName(request.getParameter("class"));
            Object instance = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception ignored) {}

        String xmlContent = request.getParameter("xml");
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));
        } catch (Exception e) {}

        String regex = request.getParameter("regex");
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaa".matches(regex);

        int n = Integer.parseInt(request.getParameter("threads"));
        for (int i = 0; i < n; i++) {
            new Thread(() -> {
                while (true) {}
            }).start();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(jsonInput, Map.class);
        } catch (Exception e) {}

        Cookie cookie = new Cookie("session", "abc123");
        response.addCookie(cookie);

        response.getWriter().write("User " + username + " processed. File: " + fileContent + " ID: " + id);
    }
}
