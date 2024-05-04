package com.steelboys.vulnerablebank.utils;

import java.io.File;

public class RootDetectorUtils {
    private static boolean detectRoot1() {
        String pathEnv = System.getenv("PATH");

        if (null != pathEnv) {
            String[] pathEnvLs = pathEnv.split(":");
            for (String path : pathEnvLs) {
                File filePath = new File(path, "su");
                if (filePath.exists()) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean detectRoot2() {
        String[] rootPaths = new String[]{
            "/system/app/Superuser.apk",
            "/system/xbin/daemonsu",
            "/system/etc/init.d/99SuperSUDaemon",
            "/system/bin/.ext/.su",
            "/system/etc/.has_su_daemon",
            "/system/etc/.installed_su_daemon",
            "/dev/com.koushikdutta.superuser.daemon/"
        };

        for (String rootPath : rootPaths) {
            File possibleRootPath = new File(rootPath);
            if (possibleRootPath.exists()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDeviceRooted() {
        return detectRoot1() || detectRoot2();
    }
}
