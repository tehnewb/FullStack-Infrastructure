package infrastructure.gdx;

import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GDXPreferences {

    private GDXPreferences() {

    }

    /**
     * Retrieves the user-specific application data directory based on the current operating system.
     * On Windows, it uses the APPDATA environment variable. On macOS, it returns "Library/Preferences",
     * and on Linux, it uses the XDG_CONFIG_HOME environment variable. If none of these are available,
     * it falls back to the ".prefs" directory.
     *
     * @return The path to the user's application data directory.
     */
    public static String getUserAppDataDirectory() {
        if (UIUtils.isWindows) {
            String appdata = System.getenv("APPDATA");
            String windir = System.getenv("WINDIR");
            return appdata != null ? appdata // 2000/XP/Vista/7/8/10/11
                    : windir != null ? windir + "/Application Data" // 95/98/Me
                    : ".prefs"; // Default to legacy directory if it's broken
        } else if (UIUtils.isMac) {
            return "Library/Preferences";
        } else if (UIUtils.isLinux) {
            String configHome = System.getenv("XDG_CONFIG_HOME");
            if (configHome != null) {
                Pattern p = Pattern.compile("(?<!\\\\)\\$(\\w+)");
                Matcher m = p.matcher(configHome);
                while (m.find()) {
                    m.reset(configHome = configHome.replaceFirst("\\Q" + m.group() + "\\E", Matcher.quoteReplacement(String.valueOf(System.getenv(m.group(1))))));
                }
            }
            return configHome != null ? configHome : ".config";

        } else return ".prefs";
    }
}
