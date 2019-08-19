import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("整理するtweet_mediaフォルダを指定してください。");
        Path TweetMediaPath = getTweetMediaPath();

        HashMap<String, ArrayList<Path>> MediaFiles = new HashMap<>();

        Files.walk(TweetMediaPath).forEach(f -> {
            if (!f.getFileName().toString().endsWith(".mp4")) return;
            String ID = f.getFileName().toString().split("-")[0];
            if (!MediaFiles.containsKey(ID)) MediaFiles.put(ID, new ArrayList<>());
            MediaFiles.get(ID).add(f);
        });

        for (String ID : MediaFiles.keySet()) {
            long MaxSize = 0;
            Path MaxSizeFile = null;
            ArrayList<Path> Paths = MediaFiles.get(ID);
            for (Path p : Paths) {
                if (MaxSize < Files.size(p)) {
                    MaxSize = Files.size(p);
                    MaxSizeFile = p;
                }
            }
            for (Path p : Paths) {
                if (!p.equals(MaxSizeFile)) {
                    System.out.println(p.getFileName().toString() + " を削除します。");
                    Files.delete(p);
                }
            }
        }

    }

    public static Path getTweetMediaPath() {
        String s = new Scanner(System.in).nextLine();
        Path TweetMediaFolder = Paths.get(s);

        if (!Files.exists(TweetMediaFolder)) {
            System.out.println("指定されたフォルダは存在しません。確認してもう一度入力してください。");
            return getTweetMediaPath();
        }

        if (!Files.isDirectory(TweetMediaFolder)) {
            System.out.println("指定されたパスはフォルダを指していません。確認してもう一度入力してください。");
            return getTweetMediaPath();
        }

        return TweetMediaFolder;
    }
}
