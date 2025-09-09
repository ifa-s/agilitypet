package com.agipet;

import net.runelite.api.Client;

import java.io.*;

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

    public void update(int start, int laps, int gained, AgiPetPlayer player) throws IOException {
        this.createDirectory();
        this.createFile();
        this.writeData(start,laps,gained,player);
    }
    private void writeData(int start, int laps, int gained, AgiPetPlayer player) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        sb.append("\n");
        sb.append(laps);
        sb.append("\n");
        sb.append(gained);
        sb.append("\n");
        sb.append(player.getEquipment()[0]);
        sb.append("\n");
        sb.append(player.getEquipment()[1]);
        sb.append("\n");
        sb.append(player.getEquipment()[2]);
        sb.append("\n");
        sb.append(player.getEquipment()[3]);
        String fn = this.getDir() + "\\pet.txt";
        FileWriter file = new FileWriter(fn, false);
        file.close();
        FileWriter f = new FileWriter(fn);
        f.append(sb);
        f.close();
    }

    public int[] readData() {
        try {
            FileReader f = new FileReader(this.getDir() + "\\pet.txt");
            BufferedReader br = new BufferedReader(f);
            String line;
            line = br.readLine();
            int xp = Integer.parseInt(line);
            line = br.readLine();
            int laps = Integer.parseInt(line);
            line = br.readLine();
            int gained = Integer.parseInt(line);
            return new int[] {xp, laps, gained};
        } catch (Exception e) {
            return new int[] {-1, -1, -1};
        }


    }
}
