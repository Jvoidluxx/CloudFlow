package cloud.main.extra;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {
    public boolean running = true;
    private long created = 0;


    public void start() {
        this.created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(
                new ReadyCallback() {
                    @Override
                    public void apply(DiscordUser discordUser) {
                        System.out.println("DiscordRP: Logged in as " + discordUser.username);
                        update("Loading Paradox Cloud...", "");
                    }
                }
        ).build();
        DiscordRPC.discordInitialize("1237046554263683183", handlers, true);
        new Thread("Discord RPC CallBack") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }
    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }
    public void update(String first, String second) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(second);
        b.setBigImage("cloudflow", "");
        b.setDetails(first);
        b.setStartTimestamps(created);
        DiscordRPC.discordUpdatePresence(b.build());
    }
}
