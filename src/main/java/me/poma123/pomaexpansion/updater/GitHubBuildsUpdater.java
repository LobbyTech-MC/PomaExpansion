package me.poma123.pomaexpansion.updater;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.UpdateCheck;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class GitHubBuildsUpdater implements Updater {
    private static final String API_URL = "https://poma123.github.io/builds/";
    private final Plugin plugin;
    private final File file;
    private final String prefix;
    private URL versionsURL;
    private URL downloadURL;
    private Thread thread;
    private final String repository;
    private String localVersion;
    private String remoteVersion;
    protected int timeout;
    protected UpdateCheck predicate;

    public GitHubBuildsUpdater(@Nonnull Plugin plugin, @Nonnull File file, @Nonnull String repo) {
        this(plugin, file, repo, "DEV - ");
        if (plugin == null) {
            throw new NullPointerException("plugin is marked non-null but is null");
        } else if (file == null) {
            throw new NullPointerException("file is marked non-null but is null");
        } else if (repo == null) {
            throw new NullPointerException("repo is marked non-null but is null");
        }
    }

    public GitHubBuildsUpdater(@Nonnull Plugin plugin, @Nonnull File file, @Nonnull String repo, @Nonnull String prefix) {
        this.timeout = 10000;
        if (plugin == null) {
            throw new NullPointerException("plugin is marked non-null but is null");
        } else if (file == null) {
            throw new NullPointerException("file is marked non-null but is null");
        } else if (repo == null) {
            throw new NullPointerException("repo is marked non-null but is null");
        } else if (prefix == null) {
            throw new NullPointerException("prefix is marked non-null but is null");
        } else {
            this.plugin = plugin;
            this.file = file;
            this.repository = repo;
            this.prefix = prefix;
            this.localVersion = this.extractBuild(plugin.getDescription().getVersion());
            this.predicate = (local, remote) -> {
                return Integer.parseInt(remote) > Integer.parseInt(local);
            };
            this.prepareUpdateFolder();
        }
    }

    private String extractBuild(String version) {
        if (version.startsWith(this.prefix)) {
            return version.substring(this.prefix.length()).split(" ")[0];
        } else {
            throw new IllegalArgumentException("Not a valid Development-Build Version: " + version);
        }
    }

    public void start() {
        try {
            this.versionsURL = new URL(API_URL + this.getRepository() + "/builds.json");
            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                this.thread = new Thread(new GitHubBuildsUpdater.UpdaterTask(), "Updater Thread");
                this.thread.start();
            });
        } catch (MalformedURLException var2) {
            this.plugin.getLogger().log(Level.SEVERE, "Auto-Updater URL is malformed", var2);
        }

    }

    public String getRepository() {
        return this.repository;
    }

    public String getLocalVersion() {
        return this.localVersion;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPredicate(UpdateCheck predicate) {
        this.predicate = predicate;
    }

    private class UpdaterTask implements Runnable {
        private UpdaterTask() {
        }

        public void run() {
            if (this.connect()) {
                try {
                    this.check();
                } catch (NumberFormatException var2) {
                    GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "Could not auto-update " + GitHubBuildsUpdater.this.plugin.getName());
                    GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "Unrecognized Version: \"" + GitHubBuildsUpdater.this.localVersion + "\"");
                }
            }

        }

        private boolean connect() {
            try {
                URLConnection connection = GitHubBuildsUpdater.this.versionsURL.openConnection();
                connection.setConnectTimeout(GitHubBuildsUpdater.this.timeout);
                connection.addRequestProperty("User-Agent", "Auto Updater (by TheBusyBiscuit)");
                connection.setDoOutput(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonObject obj = (JsonObject)(new JsonParser()).parse(reader.readLine());
                if (obj == null) {
                    GitHubBuildsUpdater.this.plugin.getLogger().log(Level.WARNING, "The Auto-Updater could not connect to github.io, is it down?");

                    try {
                        GitHubBuildsUpdater.this.thread.join();
                    } catch (InterruptedException var6) {
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "The Auto-Updater Thread was interrupted", var6);
                        Thread.currentThread().interrupt();
                    }

                    return false;
                } else {
                    GitHubBuildsUpdater.this.remoteVersion = String.valueOf(obj.get("last_successful").getAsInt());
                    GitHubBuildsUpdater.this.downloadURL = new URL(API_URL + GitHubBuildsUpdater.this.getRepository() + "/" + GitHubBuildsUpdater.this.getRepository().split("/")[1] + "-" + GitHubBuildsUpdater.this.remoteVersion + ".jar");
                    return true;
                }
            } catch (IOException var7) {
                GitHubBuildsUpdater.this.plugin.getLogger().log(Level.WARNING, "Could not connect to github.io, is it down?", var7);

                try {
                    GitHubBuildsUpdater.this.thread.join();
                } catch (InterruptedException var5) {
                    GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "The Auto-Updater Thread was interrupted", var5);
                    Thread.currentThread().interrupt();
                }

                return false;
            }
        }

        private void check() {
            if (GitHubBuildsUpdater.this.predicate.hasUpdate(GitHubBuildsUpdater.this.localVersion, GitHubBuildsUpdater.this.remoteVersion)) {
                this.install();
            } else {
                GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, GitHubBuildsUpdater.this.plugin.getName() + " is up to date!");

                try {
                    GitHubBuildsUpdater.this.thread.join();
                } catch (InterruptedException var2) {
                    GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "The Auto-Updater Thread was interrupted", var2);
                    Thread.currentThread().interrupt();
                }
            }

        }

        private void install() {
            GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, GitHubBuildsUpdater.this.plugin.getName() + " is outdated!");
            GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, "Downloading " + GitHubBuildsUpdater.this.plugin.getName() + " v" + GitHubBuildsUpdater.this.remoteVersion);
            GitHubBuildsUpdater.this.plugin.getServer().getScheduler().runTask(GitHubBuildsUpdater.this.plugin, () -> {
                BufferedInputStream input = null;
                FileOutputStream output = null;

                try {
                    input = new BufferedInputStream(GitHubBuildsUpdater.this.downloadURL.openStream());
                    output = new FileOutputStream(new File("plugins/" + Bukkit.getUpdateFolder(), GitHubBuildsUpdater.this.file.getName()));
                    byte[] data = new byte[1024];

                    int read;
                    while((read = input.read(data, 0, 1024)) != -1) {
                        output.write(data, 0, read);
                    }
                } catch (Exception var17) {
                    GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "Could not auto-update " + GitHubBuildsUpdater.this.plugin.getName(), var17);
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }

                        if (output != null) {
                            output.close();
                        }

                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, " ");
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, "#################### - UPDATE - ####################");
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, GitHubBuildsUpdater.this.plugin.getName() + " was successfully updated (" + GitHubBuildsUpdater.this.localVersion + " -> " + GitHubBuildsUpdater.this.remoteVersion + ")");
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, "Please restart your Server in order to use the new Version");
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.INFO, " ");
                    } catch (IOException var16) {
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "An Error occured while auto-updating \"" + GitHubBuildsUpdater.this.plugin.getName() + "\"", var16);
                    }

                    try {
                        GitHubBuildsUpdater.this.thread.join();
                    } catch (InterruptedException var15) {
                        GitHubBuildsUpdater.this.plugin.getLogger().log(Level.SEVERE, "The Auto-Updater Thread was interrupted", var15);
                        Thread.currentThread().interrupt();
                    }

                }

            });
        }
    }
}
