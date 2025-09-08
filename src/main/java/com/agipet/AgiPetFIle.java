package com.agipet;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.runelite.client.RuneLite.RUNELITE_DIR;
class AgiPetFile {
    private final Client client;

    public AgiPetFile(Client client) {
        this.client = client;
    }

    private void createDirectory() {

        File mainFolder = new File(RUNELITE_DIR, "AgilityPet");
        if (!mainFolder.exists()) {
            mainFolder.mkdirs();
        }
        File file = new File(mainFolder, String.valueOf(client.getAccountHash()));
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    private String getDir() {
        return RUNELITE_DIR + "\\AgilityPet\\" + client.getAccountHash();
    }
    private void createFile() throws IOException {
        String filename = "pet.txt";
        File file = new File(new File(this.getDir()), String.valueOf(client.getAccountHash()));

        if (!file.exists()) {
            boolean created = file.createNewFile();
        }
    }

    public void update(int start, int laps, int gained) throws IOException {
        this.createDirectory();
        this.createFile();
        this.writeData(start,laps,gained);
    }
    private void writeData(int start, int laps, int gained) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        sb.append("\n");
        sb.append(laps);
        sb.append("\n");
        sb.append(gained);
        String fn = this.getDir() + "\\pet.txt";
        FileWriter file = new FileWriter(fn, false);
        file.close();
        FileWriter f = new FileWriter(fn);
        f.append(sb);
        f.close();
    }
}
