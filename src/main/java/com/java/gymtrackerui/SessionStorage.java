package com.java.gymtrackerui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Properties;

public class SessionStorage {

    private static final String FOLDER = System.getProperty("user.home") + "/.gymtracker";
    private static final String FILE_PATH = FOLDER + "/session.properties";

    public static void save(String token, String email, Long userId) {

        try {
            File folder = new File(FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Properties props = new Properties();
            props.setProperty("token", token);
            props.setProperty("email", email);
            props.setProperty("userId", String.valueOf(userId));

            try (FileOutputStream out = new FileOutputStream(FILE_PATH)) {
                props.store(out, "Gym Progress Tracker session");
            }

        } catch (Exception e) {
            // If saving fails, the user will just need to log in again next time - not fatal.
            System.out.println("Could not save session: " + e.getMessage());
        }
    }

    public static boolean load() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return false;
        }

        try (FileInputStream in = new FileInputStream(file)) {

            Properties props = new Properties();
            props.load(in);

            String token = props.getProperty("token");
            String email = props.getProperty("email");
            String userId = props.getProperty("userId");

            if (token == null || email == null || userId == null) {
                return false;
            }

            Session.getInstance().setToken(token);
            Session.getInstance().setEmail(email);
            Session.getInstance().setUserId(Long.parseLong(userId));

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static void clear() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}