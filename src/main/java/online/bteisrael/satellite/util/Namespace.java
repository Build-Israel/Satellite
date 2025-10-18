package online.bteisrael.satellite.util;

import net.kyori.adventure.key.Key;
import online.bteisrael.satellite.SatelliteServer;

public enum Namespace {
    TERRA_WORLD("terra");

    final String name;

    Namespace(String name) {
        this.name = name;
    }

    public Key asKey() {
        return Key.key(SatelliteServer.SATELLITE_NAMESPACE, name);
    }
}
