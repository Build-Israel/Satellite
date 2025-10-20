package online.bteisrael.satellite.config;

import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.nio.file.Path;

@Getter
public class SatelliteConfig {

    private final Path configDir;
    private String secret;
    private String address;
    private short port;
    private Component messageOfTheDay;

    public SatelliteConfig(Path configDir) {
        this.configDir = configDir;
        this.reloadConfig();
    }

    private void reloadConfig() {

    }


}
